package org.choongang.commons;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.controllers.BasicConfig;
import org.choongang.admin.config.service.ConfigInfoService;
import org.choongang.file.service.FileInfoService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
@RequiredArgsConstructor
public class Utils {

    private final HttpServletRequest request;
    private final HttpSession session;
    private  FileInfoService fileInfoService;
    private final ConfigInfoService infoService;

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


    public static String getMessage(String code, String type)
    {
        type=StringUtils.hasText(type) ? type : "validations";

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
        return getMessage(code, null);
    }

    /**
     * \n 또는 \r\n -> <br>
     * @param str
     * @return
     */
    public String nl2br(String str) {
        str = Objects.requireNonNullElse(str, "");
        str = str.replaceAll("\\n","<br>")
                .replaceAll("\\r","");

        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     *
     * @return
     */
    public List<int[]> getThumbSize() {
        BasicConfig basicConfig = (BasicConfig)request.getAttribute("siteConfig");
        String thumbSize = basicConfig.getThumbSize(); // \r\n
        String[] thumbsSize = thumbSize.split("\\n");


        List<int[]> data = Arrays.stream(thumbsSize)
                .filter(StringUtils::hasText)
                .map(s -> s.replaceAll("\\s+", ""))
                .map(this::toConvert).toList();


        return data;
    }


    private int[] toConvert(String size) {
        size = size.trim();
        int[] data = Arrays.stream(size.replaceAll("\\r","").
                toUpperCase().split("X")).mapToInt(Integer::parseInt).toArray();

        return data;
    }

    public String printThumb(long seq, int width, int height, String className) {
        String[] data = fileInfoService.getThumb(seq, width, height);
        if(data != null) {
            String cls = StringUtils.hasText(className) ? " class='" + className + "'" : "";
            String image = String.format("<img src='%s'%s>", data[1], cls);
            return image;
        }
        return "";
    }
    public String printThumb(long seq, int width, int height) {
        return printThumb(seq, width, height, null);
    }

    /**
     * 0이하 정수 인 경우 1이상 정수로 대체
     *
     * @param num
     * @param replace
     * @return
     */
    public static int onlyPositiveNumber(int num, int replace) {
        return num < 1 ? replace : num;
    }

    /**
     * API 설정 조회
     *
     * @param key
     * @return
     *
     */
    public String getApiConfig(String key) {
        Map<String, String> config = infoService.get("apiConfig", new TypeReference<Map<String, String>>() {
        }).orElse(null);
        if (config == null) return "bf7aec0c3a4f12640bb15901ed8550f7";

        return config.getOrDefault(key, "");
    }
}
