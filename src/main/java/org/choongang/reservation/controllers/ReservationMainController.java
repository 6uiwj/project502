package org.choongang.reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.calendar.Calendar;
import org.choongang.commons.Utils;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationMainController {
    private final ReservationRepository reservationRepository;
    private final Utils utils;
    private final Calendar calendar;

    @GetMapping("/main")
    public String mainPage() {
        return utils.tpl("reservation/main");

    }

    @GetMapping("/center_choice")
    public String centerChoicePage() {
        return utils.tpl("reservation/center_choice");
    }

    @GetMapping("/time_choice")
    public String timeChoicePage() {
        return utils.tpl("reservation/time_choice");
    }

    @GetMapping("/userInfo_check")
    public String userInfoCheckPage() {
        return utils.tpl("reservation/userInfo_check");
    }

    @GetMapping
    public String index(@RequestParam(name="year", required = false) Integer year,
                        @RequestParam(name="month", required = false) Integer month, Model model) {
        //쿼리스트링으로 연과 월을 넣엊루 것임

        Map<String, Object> data = calendar.getData(year, month);
        //맵에 킷값을 가지고 바로 접근
        model.addAllAttributes(data);


        model.addAttribute("addCss", new String[] { "calendar/style"});
        model.addAttribute("addCommonScript", new String[] { "calendar" });

        return utils.tpl("reservation/time_choice");

    }
}

