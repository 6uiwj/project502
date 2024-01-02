package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;
    //3가지 메시지 번들 가져오기 (messages > *.properties 파일들)
    private static final ResourceBundle commonsBundle;

    //유효성검사과 관련된 메시지 코드
    private static final ResourceBundle validationsBundle;

    private static final ResourceBundle errorsBundle;

    //초기화 (static이라 객체를 만들지 않아도 초기화 ..)
    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");

    }

    public boolean isMobile() {
        //모바일 수동전환 모드 체크
        String device = (String)session.getAttribute("device");
        if(StringUtils.hasText(device))  {
            return device.equals("MOBILE");
        }

        //요청 헤더: User-Agent 정보를 가지고 모바일인지 아닌지 체크할 것임
        String ua = request.getHeader("User-Agent");
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        return ua.matches(pattern);

    }

    public String tpl(String path) {
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }

    public static String getMessage(String code, String type) {
        type = StringUtils.hasText(type) ? type : "validations";

        ResourceBundle bundle = null;
        if(type.equals("commons")) {
            bundle = commonsBundle;
        } else if (type.equals("errors")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        return bundle.getString(code);
    }
    public static String getMessage(String code) {
        return getMessage(code,null);
    }
}
