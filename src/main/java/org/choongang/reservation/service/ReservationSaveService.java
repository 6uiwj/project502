package org.choongang.reservation.service;


import lombok.RequiredArgsConstructor;
import org.choongang.admin.reservation.controllers.RequestReservation;
import org.choongang.center.constants.DonationType;
import org.choongang.member.MemberUtil;
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

        //수정모드인지 추가모드인지 확인
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add_reservation";
        //모드값이 있으면 그 모드로 하고, 없으면 add모드

        //폼으로부터 입력받은 데이터를 커맨드객체에서 받고 커맨드 객체 안의 BookCOde를 변수에 저장
        Long bookCode = form.getBookCode();

        //커맨드객체에 들어온 값을 DB Reservation table에 넣을 것임
        Reservation data = null;
        //수정모드이면 DB에서 수정하려는 예약자의 bookCode를 조회하여 정보를 data에 넣고, 없으면 예외로 떤져!
        if (mode.equals("edit_reservation") && bookCode != null) {
            data = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);
        } else {
            //추가모드이면 엔티티를 가져오고 회원쪽에서 로그인정보를 가져와 담는다.
            data =new Reservation();
            data.setMember(memberUtil.getMember()); //Member의 정보를 가져옴
        }

        data.setDonorTel(form.getDonorTel());





        /*
        //날짜랑 시간 입력받은거 20240124 이런형식으로 출력하려했는데 할 필요가 없어졌네?
        String bookDate = form.getBookDate();
        String dates=null;
        if(StringUtils.hasText(bookDate)) {
            dates = Arrays.stream(bookDate.split("-")).collect(Collectors.joining(""));
        }
        String BookDateTime  = String.format("%s%s", form.getBookHour(),form.getBookMin());
        String bookDateTime = dates.concat(BookDateTime);

        */



        //커맨드객체에서 헌혈타입 가져오기
        data.setBookType(DonationType.valueOf(form.getBookType()));

        //resrevation 엔티티에 넣고 저장
        reservationRepository.saveAndFlush(data);
        System.out.println("데이터 들어가니?");
        return data;
    }

}
