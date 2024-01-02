package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.service.JoinService;
import org.choongang.member.service.MemberInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;

    @GetMapping("/join") //회원가입 양식(form) 주소
    public String join(@ModelAttribute RequestJoin form) {

        return utils.tpl("member/join");
    }

    //회원가입처리
    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {

        joinService.process(form, errors);

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

    /*
    @ResponseBody //아?
    @GetMapping("/info")
    public void info(Principal principal) {
        String username = principal.getName();
        System.out.printf("username=%s%n", username);
    }

     */

    /*
    @ResponseBody //아?
    @GetMapping("/info")
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        System.out.println(memberInfo);
    }

     */

    @ResponseBody //아?
    @GetMapping("/info")
    public void info() {
        MemberInfo memberInfo = (MemberInfo)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println(memberInfo);
    }
}
