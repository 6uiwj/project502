package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {

    private final ReservationRepository repository;
    private final ReservationInfoService infoService;
    public void delete(Long bookCode) {
        Reservation reservation = infoService.get(bookCode);

        repository.delete(reservation);
        repository.flush();

    }
}
