package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//조회에 대한 서비스
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    //회원 인증시 회원정보조회

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;
    private final EntityManager em;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username) //이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) //아이디로 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));
        //GrantedAuthority의 하위 클래스
        List<SimpleGrantedAuthority> authorities= null;
        List<Authorities> tmp = member.getAuthorities();
        //getAuthorities : Authorities 테이블 데이터를 가져옴(=상수)
        if(tmp != null) { //tmp값이 있으면
            authorities = tmp.stream()//스트림을 쓰면 됨(map을 통해 변환)
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name())) //넘어온 값(상수)을 문자열로 변환
                    .toList(); //문자열을 리스트형태로 변환
        }


        /* 프로필 이미지 처리 S */
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if (files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }
        /* 프로필 이미지 처리 E */

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
    //Page : page정보가 담겨있는 형태를 임시로 반환값으로..
    //매개변수 MemberSearch search :목록 데이터 가져오기

    /**
     * 회원목록
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search) {
        //onlyPositiveNumber을 이용하여 레코드개수가 없거나 음수면 특정 고정값 지정
        int page = Utils.onlyPositiveNumber(search.getPage(),1); //페이지 번호
        int limit = Utils.onlyPositiveNumber(search.getLimit(),20);  //1페이지당 레코드 개수
        int offset = ( page -1 ) * limit; //레코드 시작 위치 번호(0, 20, 40..)

        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        //queryDSL내에서 직접 쿼리 시 - pathbuilder = 정렬할 때 사용
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");

        //회원목록 가져오기
        List<Member> items = new JPAQueryFactory(em)
                .selectFrom(member) //멤버 선택 후
                .leftJoin(member.authorities) //조인
                .fetchJoin()            //지연로딩하기 때문에 즉시로딩으로 바꾸기 위해 가져옴
                .where(andBuilder)
                .limit(limit)  //페이지 개수
                .offset(offset)  //현재 레코드가 얼만큼 써져 있는지 (= 시작위치가 어딘지)
                                 //1p 0~19레코드 / 2p 20~39레코드 출력
                .orderBy(new OrderSpecifier(Order.DESC,pathBuilder.get("createdAt")))
                //가입일자 순으로 내림차순(가입을 최근에 한 순서대로 나오도록)
                .fetch();

        //페이지에 따라 데이터로 바뀌어야 하므로

        /* 페이징 처리 S -매개변수에 있는 값들만 전부 완성시켜주면 됨 */
        int total = (int)memberRepository.count(andBuilder); //총 레코드 개수

        //현재페이지, 전체레코드 개수. 페이지 개수, 한페이지당 레코드 개수, request
        Pagination pagination = new Pagination(page, total, 10, limit, request);
        /*페이징 처리 E */
        //전체페이지개수와 request를 채워주자...
        return new ListData<>(items, pagination); //리스트 데이터와 페이징데이터를 반환


    }
}
