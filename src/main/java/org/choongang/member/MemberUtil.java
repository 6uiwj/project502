package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;
    //세션데이터가 있으면 로그인한 것 / 세션데이터가 없으면 로그인 X
    public boolean isLogin() {
        return getMember() != null;

    }
    public Member getMember() {
        Member member = (Member) session.getAttribute("member"); //성공시 값
        return member;
    }

    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }
}
