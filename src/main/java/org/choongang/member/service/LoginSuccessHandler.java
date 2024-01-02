package org.choongang.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    // 매개변수 :  (요청/응답/예외 객체)
    //로그인 성공시 회원 정보가 Authentication에 들어있다?
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MemberUtil.clearLoginData(session);
        //Authentication에 있는 정보를 이용하자
        /* 회원 정보 조회 편의 구현 */
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member",member);

        String redirectURL = request.getParameter("redirectURL");

        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/";

        response.sendRedirect(request.getContextPath()+redirectURL);
    }
}
