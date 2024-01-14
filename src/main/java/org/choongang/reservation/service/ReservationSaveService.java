package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.center.constants.DonationType;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final ReservationRepository reservationRepository;
    Reservation reservation = new Reservation();
    public Reservation save(String name, String donnerTel, DonationType bookType, String center) {
        reservation.getMember().setName(name);
        reservation.setDonnerTel(donnerTel);
        reservation.setBookType(bookType);
        reservation.getCenter().setCName(center);
        this.reservationRepository.save(reservation);

        return reservation;
    }

}
