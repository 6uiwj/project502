package org.choongang.commons;

import lombok.RequiredArgsConstructor;
import org.choongang.admin.controllers.BasicConfig;
import org.choongang.admin.sersvice.ConfigInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class BasicConfigAdvice {
    private final ConfigInfoService infoService;

    @ModelAttribute("siteConfig")
    public BasicConfig getBasicConfig() {
        BasicConfig config = infoService.get("basic", BasicConfig.class).orElseGet(BasicConfig::new);
        return config;
    }


}
