package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.Authority;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

//회원가입기능
@Service
@RequiredArgsConstructor //의존성이 필요하기 때문에..?
@Transactional
public class JoinService {

    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    public void process(RequestJoin form, Errors errors) {
        validator.validate(form, errors);
        if(errors.hasErrors()) {
            return;
            //검증 실패 시 다음 로직을 실행시키지 않고 중단
        }
        //검증 성공시 가입처리
        //비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        //Member member = new ModelMapper().map(form, Member.class);
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash);
        member.setUserId(form.getUserId());

        //DB에 저장처리
        process(member);

        //회원 가입시에는 일반 사용자 권한 부여(USER)
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);
    } //entity?

    public void process(Member member) {
        memberRepository.saveAndFlush(member); //커맨드 객체 형태로 값이 들어온다.
    }
}
