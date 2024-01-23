package org.choongang.reservation.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationSearch {


    private int page = 1; //페이지 번호
    private int limit = 20;  //페이지당 게시글 수

    private List<Long> memberSeq; //회원번호
    private List<String> userId; //회원ID

    //private String userId; //마이페이지에서도 조회가 아능하게
    private List<Long> bookCode; //예약코드

    // 예약일자 범위
    private LocalDate sDate; //예약시작날짜
    private LocalDate eDate; //예약 끝 날짜
    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드


}
