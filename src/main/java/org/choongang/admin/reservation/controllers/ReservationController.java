package org.choongang.admin.reservation.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.ListData;
import org.choongang.reservation.controllers.ReservationSearch;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.service.ReservationInfoService;
import org.choongang.reservation.service.ReservationSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@Controller
@RequestMapping("/admin/reservation")
@RequiredArgsConstructor
public class ReservationController implements ExceptionProcessor {

        private final ReservationInfoService infoService;
        private final ReservationSaveService saveService;
        private final ReservationValidator validator;

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
        public String list(@ModelAttribute ReservationSearch search, Model model) {
            commonProcess("list", model);
            ListData<Reservation> data = infoService.getList(search);

            model.addAttribute("items", data.getItems()); //목록
            model.addAttribute("pagination",data.getPagination());//페이징

            return "admin/reservation/list";
        }


        /**
         * 예약 등록
         * @param model
         * @return
         */
        @GetMapping("/add_reservation")
        public String addReservation(Model model) {
            commonProcess("add_reservation",model);
            return "admin/reservation/add_reservation";
        }


        /**
         * 예약 저장
         * @param model
         * @return
         */

        @PostMapping("/save_reservation")
        public String saveReservation(@Valid RequestReservation form, Errors errors, Model model) {
            String mode = form.getMode();
            //여기 유효성 검사안함...ㅎㅎ
            commonProcess(mode, model);
            validator.validate(form,errors);


            if (errors.hasErrors()) {
                errors.getAllErrors().stream().forEach(System.out::println);
                return "admin/reservation/" + mode;
            }

            Reservation data = saveService.save(form);
            return "redirect:/admin/reservation";
        }


    /**
     * 예약 수정
     * @param bookCode
     * @param model
     * @return
     */

        @GetMapping("/edit/{bookCode}")
        public String edit(@PathVariable("bookCode") Long bookCode, Model model) {
            commonProcess("edit_reservation", model);

            RequestReservation form = infoService.getForm(bookCode);
            System.out.println(form);
            model.addAttribute("requestReservation", form);



            return "admin/reservation/edit_reservation";
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


            } else if(mode.equals("add_reservation")) {
                pageTitle = "예약 등록";
            }
            model.addAttribute("pageTitle", pageTitle);
            model.addAttribute("subMenuCode", mode);
        }
    }





