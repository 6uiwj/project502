package org.choongang.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    // 매개변수 :  (요청/응답/예외 객체)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //속성을 통해 메시지 전달 (페이지 이동이 있기 때문에 데이터유지를 위해 request보다 큰 범위인 session 이용)
        HttpSession session = request.getSession();

        //세션 로그인 실패 메시지 일괄 삭제
        MemberUtil.clearLoginData(session);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        session.setAttribute("username", username);

        if(!StringUtils.hasText(username)) {
            //validations.properties의 메시지를 이용하자.
            session.setAttribute("NotBlank_username", Utils.getMessage("NotBlank.userId"));
        }

        if(!StringUtils.hasText(password)) {
            session.setAttribute("NotBlank_password",Utils.getMessage("NotBlank.password"));
        }

        //아이디, 비번이 있지만 실패한 경우 : 아이디로 조회되는 회원이 없거나, 비번이 일치 X
        if(StringUtils.hasText(username) && StringUtils.hasText(password)) {
            session.setAttribute("Global_error", Utils.getMessage("Fail.login", "errors"));
        }

       //로그인 페이지로 이동
        response.sendRedirect(request.getContextPath()+"/member/login");
    }
}
