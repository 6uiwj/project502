package org.choongang.admin.reservation.controllers;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
@Controller
@RequestMapping("/admin/reservation")
public class ReservationController implements ExceptionProcessor {

        //주메뉴 불러오기
        @ModelAttribute("menuCode")
        public String getMenuCode() {
            return "reservation";
        }

        //서브메뉴 불러오기
        @ModelAttribute("subMenus")
        public List<MenuDetail> getSubMenus() {
            return Menu.getMenus("reservation");
        }


        /**
         * 예약 현황, 예약 관리
         *
         * @param model
         * @return
         */
        @GetMapping
        public String list(Model model) {
            commonProcess("list", model);

            return "admin/reservation/list";
        }


        /**
         * 예약 등록
         * @param model
         * @return
         */
        @GetMapping("/add")
        public String addReservation(Model model) {
            commonProcess("add",model);
            return "admin/reservation/add_reservation";
        }


        /**
         * 예약 추가, 저장
         * @param model
         * @return
         */

        @PostMapping("/save_reservation")
        public String saveBranch(Model model) {
            return "redirect:/admin/reservation";
        }



        /**
         * 공통 처리
         *
         * @param mode
         * @param model
         */
        private void commonProcess(String mode, Model model) {
            String pageTitle = "헌혈 예약자 목록";
            //기본값 : list
            mode = Objects.requireNonNullElse(mode, "list");

            if (mode.equals("edit_reservation")) {
                pageTitle = "예약정보 수정";


            } else if(mode.equals("add")) {
                pageTitle = "예약 등록";
            }
            model.addAttribute("pageTitle", pageTitle);
            model.addAttribute("subMenuCode", mode);
        }
    }





