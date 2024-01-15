package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.center.constants.DonationType;
import org.choongang.member.MemberUtil;
import org.choongang.reservation.controllers.RequestReservation;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

//예약하고 싶은 회원의 정보를 받아서 예약정보를 저장하는 서비스
@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final ReservationRepository reservationRepository;

    private final MemberUtil memberUtil;

    public Reservation save(RequestReservation form) {


        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";
        Long bookCode = form.getBookCode();

        Reservation reservation = null;
        if (mode.equals("edit") && bookCode != null) {
            reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);
        } else {
            reservation = new Reservation();
            reservation.setMember(memberUtil.getMember());
        }

        reservation.setDonnerTel(form.getDonnerTel());
        reservation.setBookType(DonationType.valueOf(form.getBookType()));


        reservationRepository.saveAndFlush(reservation);

        return reservation;
    }

}
