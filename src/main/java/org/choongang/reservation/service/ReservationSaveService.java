package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.admin.reservation.controllers.RequestReservation;
import org.choongang.center.constants.DonationType;
import org.choongang.member.MemberUtil;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

//예약하고 싶은 회원의 정보를 받아서 예약정보를 저장하는 서비스
@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final ReservationRepository reservationRepository;

    private final MemberUtil memberUtil;

    public Reservation save(RequestReservation form) {


        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add_reservation";
        Long bookCode = form.getBookCode();

        Reservation data = null;
        if (mode.equals("edit_reservation") && bookCode != null) {
            data = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);
        } else {
            data =new Reservation();
            data.setMember(memberUtil.getMember());
        }

        String donorTel = Arrays.stream(form.getDonorTel()).collect(Collectors.joining("-"));
        data.setDonorTel(donorTel);
        data.setBookType(DonationType.valueOf(form.getBookType()));


        reservationRepository.saveAndFlush(data);

        return data;
    }

}
