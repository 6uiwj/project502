package org.choongang.admin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.choongang.admin.controllers.BasicConfig;
import org.choongang.admin.sersvice.ConfigInfoService;
import org.choongang.admin.sersvice.ConfigSaveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Transactional
@TestPropertySource(properties = "spring.profiles.active=test")
public class ConfigSaveTest {
    @Autowired
    private ConfigSaveService saveService;
    @Autowired
    private ConfigInfoService infoService;

    @Test
    @DisplayName("BasicConfig로 생성된 객체가 JSON으로 저장되는지 테스트")
    void saveTest() {
        BasicConfig config = new BasicConfig();
        config.setSiteTitle("사이트 제목");
        config.setSiteDescription("사이트 설명");
        config.setSiteKeywords("사이트 키워드");
        config.setCssJsVersion(1);
        config.setJoinTerms("회원가입 약관");

        saveService.save("basic", config);

        BasicConfig config2 = infoService.get("basic", BasicConfig.class).get();
        System.out.println(config2);

        Optional<Map<String, String>> opt = infoService.get("basic", new TypeReference<>() {});
        System.out.println(opt.get());
    }


}
