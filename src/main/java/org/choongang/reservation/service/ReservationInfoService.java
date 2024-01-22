package org.choongang.reservation.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.reservation.controllers.ReservationSearch;
import org.choongang.reservation.entities.QReservation;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationRepository reservationRepository;
    private final HttpServletRequest request;

    //예약코드로 조회 (기본키)
    public Reservation get(Long bookCode) {

        Reservation reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);

        // 추가 처리

        return reservation;
    }

    //조건 조회
    public ListData<Reservation> getList(ReservationSearch search) {

        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder(); //조건식을 쓰기 위해

        /* 검색 조건 처리 S */


        List<Long> memberSeq = search.getMemberSeq(); // 회원번호
        List<Long> bookCode = search.getBookCode(); // 예약 번호
        List<String> userId = search.getUserId(); // 회원아이디
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        // 회원번호로 조회
        if (memberSeq != null && !memberSeq.isEmpty()) {
            andBuilder.and(reservation.member.seq.in(memberSeq));
        }

        // 예약번호 조회
        if (bookCode != null && !bookCode.isEmpty()) {
            andBuilder.and(reservation.bookCode.in(bookCode));
        }

        // 회원 아이디 
        if (userId != null && !userId.isEmpty()) {
            andBuilder.and(reservation.member.userId.in(userId));
        }

        /* 예약 날짜 조회 */
        if (sDate != null) {
            andBuilder.and(reservation.bookDateTime.goe(LocalDateTime.of(sDate, LocalTime.of(0, 0, 0))));
        }

        if (eDate != null) {
            andBuilder.and(reservation.bookDateTime.loe(LocalDateTime.of(eDate, LocalTime.of(23, 59, 59))));
        }

        /* 검색 조건 처리 E */

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        Pageable pageable = PageRequest.of(page, limit, Sort.by(desc("createdAt")));
        Page<Reservation> data = reservationRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);

    }
}
