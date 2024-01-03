package org.choongang.admin.controllers;

import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController implements ExceptionProcessor {

    // /admin했을 ㄸ ㅐ처음 보이는 페이지
    @GetMapping
    public String index() {
        return "admin/main/index";
    }
}
