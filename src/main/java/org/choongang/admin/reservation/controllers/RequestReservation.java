package org.choongang.admin.reservation.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class RequestReservation {
    //예약 관리자페이지 커맨드 객체

        private String mode = "add"; //모드: 예약 수정

        private long bookCode; //예약코드

        private String donorName; //예약자이름

        //@NotBlank
        private long userNo; //회원번호

        @NotBlank
        private String[] donorTel; //헌혈자 전화번호

        @NotBlank
        private String bookType; //헌혈종류

       // @NotBlank
        private long cCode; //헌혈의집 코드

        private String center; //센터명
        private String bookDate; //예약 날짜
        private String bookHour; // 예약 시간
        private String bookMin; // 예약 분

    }

