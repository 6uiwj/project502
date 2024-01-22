package org.choongang.calendar.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.calendar.Calendar;
import org.choongang.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final Calendar calendar;
    private final Utils utils;
    //달력도 mobile view, frontview 다를 수있음

    /*
    @GetMapping
    public String index(@RequestParam(name="year", required = false) Integer year,
                        @RequestParam(name="month", required = false) Integer month, Model model) {
        //쿼리스트링으로 연과 월을 넣어줄 것임

        Map<String, Object> data = calendar.getData(year, month);
        //맵에 킷값을 가지고 바로 접근
        model.addAllAttributes(data);


        model.addAttribute("addCss", new String[] { "calendar/style"});
        model.addAttribute("addCommonScript", new String[] { "calendar" });

        return utils.tpl("calendar/index");

    }

     */
}
