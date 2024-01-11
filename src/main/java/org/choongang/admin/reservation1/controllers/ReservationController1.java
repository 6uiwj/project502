package org.choongang.admin.reservation1.controllers;

import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

public class ReservationController1 {
    @Controller
    @RequestMapping("/admin/reservation")
    public class ReservationController implements ExceptionProcessor {

        @ModelAttribute("menuCode")
        public String getMenuCode() {
            return "reservation";
        }

        @GetMapping
        public String list(Model model) {
            commonProcess("list", model);

            return "admin/reservation/list";
        }


        private void commonProcess(String mode, Model model) {
            String pageTitle = "헌혈 예약자 목록";
            mode = Objects.requireNonNullElse(mode, "list");

            if(mode.equals("edit_reservation")) {
                pageTitle = "예약정보 수정";

            } else if (mode.equals("")){}
            }
        }

    }



