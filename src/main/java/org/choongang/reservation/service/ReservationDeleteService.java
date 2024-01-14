package org.choongang.reservation.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {
    private final ReservationRepository reservationRepository;
    public Reservation delete(Member member) {
        Reservation reservation = reservationRepository.findByName(member.getName());
        this.reservationRepository.delete(reservation);

        return reservation;
    }


}
