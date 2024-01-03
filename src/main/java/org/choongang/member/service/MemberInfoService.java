package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
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

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
