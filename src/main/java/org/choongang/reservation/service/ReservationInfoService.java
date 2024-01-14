package org.choongang.reservation.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;

    //예약자 이름(Member엔티티의 name값으로 조회)
    public Reservation get(Member member) {
       Reservation reservation = reservationRepository.findByName(member.getName());

       return reservation;



    }
}
