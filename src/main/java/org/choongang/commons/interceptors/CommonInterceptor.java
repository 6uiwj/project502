package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

//공통 인터셉터
@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request);
        return true;
    }

    /**
     * PC, 모바일 수동 변경 처리
     * device - PC : PC뷰,
     *          Mobile : Mobile 뷰
     */
    //세션을 가지고 등록
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device");
        //값이 있을 때만 처리
        //if(device == null || device.isBlank())
        if (!StringUtils.hasText(device)) {
            return;
        }

        device =device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        HttpSession session = request.getSession();
        session.setAttribute("device",device);
    }

}
