package org.choongang.reservation;

import org.choongang.admin.reservation.controllers.RequestReservation;
import org.choongang.reservation.controllers.ReservationSearch;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.service.ReservationDeleteService;
import org.choongang.reservation.service.ReservationInfoService;
import org.choongang.reservation.service.ReservationSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class ReservationInfoTest {

    @Autowired
    private ReservationInfoService infoService;

    @Autowired
    private ReservationSaveService saveService;

    @Autowired
    private ReservationDeleteService deleteService;

    private RequestReservation form;

    private ReservationSearch search;

    @BeforeEach
    void init() {
        form = new RequestReservation();
        form.setDonorName("이름이다");
       // form.setDonorTel(new String[]{"010","1234","5678"});
        form.setBookType("ALL");
        form.setCenter("서울지점");
        form.setBookDate("2024-01-29");
        form.setBookHour("3시");
        form.setBookMin("20분");

        Reservation reservation = saveService.save(form);
    }
    @Test
    void test1() {
      //  infoService.getList(search.get)
    }
}
