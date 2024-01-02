package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;

    @GetMapping("/join") //회원가입 양식(form) 주소
    public String join(@ModelAttribute RequestJoin form) {
        return utils.tpl("member/join");
    }

    //회원가입처리
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {
        if(errors.hasErrors()) {
            //에러가 발생했을 시 넘기지 않고 템플릿 쪽을 그대로 출력
            return utils.tpl("member/join");
        }
        return "redirect:/member/login"; //로그인 페이지로 이동
    }
    @GetMapping("/login")
    public String login(){
        return utils.tpl("member/login");
    }
}
