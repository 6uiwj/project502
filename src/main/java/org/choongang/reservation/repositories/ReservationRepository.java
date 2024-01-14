package org.choongang.reservation.repositories;

import org.choongang.member.entities.Member;
import org.choongang.reservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation> {
    Reservation findByName(String name);

}
