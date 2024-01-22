package org.choongang.reservation.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.board.controllers.RequestBoardConfig;
import org.choongang.admin.reservation.controllers.RequestReservation;
import org.choongang.board.entities.Board;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.reservation.controllers.ReservationSearch;
import org.choongang.reservation.entities.QReservation;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
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
    private Util util;
    EntityManager em;

    //예약자 이름(Member엔티티의 name값으로 조회)

    public Reservation get(Long bookCode) {

        Reservation reservation = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);

        // 추가 처리

        return reservation;
    }



    public RequestReservation getForm(Long bookCode){
        Reservation reservation = get(bookCode); //사용자가 요청한 bookCode와 일치하는 정보를 DB에서 가져옴

        //DB에 조회한 내용을 커맨드객체에 담는다(=RequestReservation)
        RequestReservation form = new ModelMapper().map(reservation, RequestReservation.class);

        form.setBookCode(reservation.getBookCode());
        form.setBookCode(reservation.getBookCode());
        form.setDonorName(reservation.getMember().getName());
        form.setDonerTel(reservation.getDonnerTel());
        form.setBookType(reservation.getBookType().name());
        form.setCenter(reservation.getCenter().getCName());
        //예약시간은 커맨드객체에 예약날짜(bookDate)+예약 시간(bookHour)+예약분(bookMin)으로 쪼개져 있는데
        //엔티티에선 LocaldateTime bookDateTime으로 합쳐져 있는데 어떻게 담지..

        form.setMode("edit_reservation");



        return null;
    }

    public ListData<Reservation> getList(ReservationSearch search) {

        QReservation reservation = QReservation.reservation;
        BooleanBuilder andBuilder = new BooleanBuilder();

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
