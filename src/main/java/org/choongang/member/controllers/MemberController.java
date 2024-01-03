package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;

    private final MemberUtil memberUtil;
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
    /*
    @ResponseBody //아?
    @GetMapping("/info")
    public void info() {
        MemberInfo memberInfo = (MemberInfo)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        System.out.println(memberInfo);
    }

     */
    @ResponseBody //로그인하면 회원정보를 보여주고 로그인상태가 아니면 비로그인 상태라고 보여줄 것임
    @GetMapping("/info")
    public void info() {
        if(memberUtil.isLogin()) { //로그인 상태체크
            Member member = memberUtil.getMember();
            System.out.println(member);  //회원정보 출력
        } else {
            System.out.println("미로그인 상태..");
        }
    }
}
