package org.choongang.reservation.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.reservation.controllers.RequestReservation;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.reservation.controllers.ReservationSearch;
import org.choongang.reservation.entities.QReservation;
import org.choongang.reservation.entities.Reservation;
import org.choongang.reservation.repositories.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public RequestReservation getForm(Long bookCode){
        Reservation reservation = get(bookCode); //사용자가 요청한 bookCode와 일치하는 정보를 DB에서 가져옴

        //DB에 조회한 내용을 커맨드객체에 담는다(=RequestReservation)
        //modelMapper : 객체 간의 매핑을 도와줌(reservation 객체를 RequestReservation 클래스로 매핑함) -> 일치하는 부분에 대해서만 자동 매핑함
        RequestReservation form = new ModelMapper().map(reservation, RequestReservation.class);

        String donorTel = reservation.getDonorTel();

        form.setBookCode(reservation.getBookCode());
        form.setDonorName(reservation.getMember().getName());
        form.setDonorTel(donorTel);
        form.setBookType(reservation.getBookType().name());
        form.setCenter(reservation.getCenter().getCName());
        //예약시간은 커맨드객체에 예약날짜(bookDate)+예약 시간(bookHour)+예약분(bookMin)으로 쪼개져 있는데
        //엔티티에선 LocaldateTime bookDateTime으로 합쳐져 있는데 어떻게 담지..

        form.setMode("edit_reservation");



        return form;
    }
    //조건 조회
    public ListData<Reservation> getList(ReservationSearch search) {

        QReservation reservation = QReservation.reservation;


        BooleanBuilder andBuilder = new BooleanBuilder(); //조건식을 쓰기 위해

        /* 검색 조건 처리 S */


        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt : "all";


        //검색조건 - 예약자명, 예약코드, 예약센터 24-01-25
        if(StringUtils.hasText(skey)) {
            skey = skey.trim(); //공백제거

            //엔티티 데이터에 skey(검색어)와 일치하는 게 있는가?
            BooleanExpression cNameCond = reservation.center.cName.contains(skey);
            BooleanExpression nameCond = reservation.member.name.contains(skey);
            BooleanExpression bookCodeCond = reservation.bookCode.stringValue().contains(skey);

            if(sopt.equals("cName")) { //센터명으로 검색
                andBuilder.and(cNameCond);
            } else if(sopt.equals("mName")) { //예약자명으로 검색

            } else { //예약코드로 검색

            }
        }
        //24-01-25 end



        List<Long> memberSeq = search.getMemberSeq(); // 회원번호
        List<Long> bookCode = search.getBookCode(); // 예약 번호
        List<String> userId = search.getUserId(); // 회원아이디
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();

        // 회원번호로 조회
        if (memberSeq != null && !memberSeq.isEmpty()) {
            //select * from reservations where 검색한memberseq in (memberSeq);
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

        //page: 보여줄 페이지    limit: 한페이지당 출력할 데이터 수
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        Pageable pageable = PageRequest.of(page, limit, Sort.by(desc("createdAt")));
        Page<Reservation> data = reservationRepository.findAll(andBuilder, pageable);

        //페이지블록에 출력할 페이지수
        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        return new ListData<>(data.getContent(), pagination);

    }
}
