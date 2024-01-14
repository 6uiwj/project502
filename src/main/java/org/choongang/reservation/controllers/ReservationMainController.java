package org.choongang.reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.Utils;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationMainController {
    private final ReservationRepository reservationRepository;
    private final Utils utils;

    @GetMapping("/main")
    public String mainPage() {
        return utils.tpl("reservation/main");

    }
}

