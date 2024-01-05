package org.choongang.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.controllers.BasicConfig;
import org.choongang.admin.sersvice.ConfigInfoService;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

//공통 인터셉터
@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {
    private final ConfigInfoService infoService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request);
        clearLoginData(request);
        loadSiteConfig(request);
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
    //매개변수가 request인 이유 : 현재 접속주소를보고 로그인페이지가 아니면 세션을 비운다.
    //getrequestURI 이용
    //세션을 지우기 위해 세션 객체 필요 (request이용)
    private void clearLoginData(HttpServletRequest request) {
        String URL = request.getRequestURI();
        //String의 contains 혹은 indexof 이용
        if(URL.indexOf("/member/login") == -1) {
            HttpSession session = request.getSession();
            MemberUtil.clearLoginData(session);
        }
    }

    private void loadSiteConfig(HttpServletRequest request) {
        String[] excludes = {".js", ".css", ".png", ".jpg", ".jpeg", "gif", ".pdf", ".xls",
        ".xlxs", ".ppt"}; //URL에 이런 파일이 추가되었다면 이 파일들은 설정을 공유할 필요가 없다?(로드배제)

        String URL = request.getRequestURI().toLowerCase();
        boolean isIncluded = Arrays.stream(excludes).anyMatch(s -> URL.contains(s));
        if (isIncluded) {
            return;
        }

        BasicConfig config = infoService.get("basic", BasicConfig.class)
            .orElseGet(BasicConfig::new);
            request.setAttribute("siteConfig", config);

    }
}
