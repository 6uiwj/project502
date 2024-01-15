package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

//예약 테이블과 조인 된 멤버테이블에서 멤버의 이름(name)을 가져와서
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
