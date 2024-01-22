package org.choongang.reservation.controllers;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReservationSearch {
    private int page = 1;
    private int limit = 20; //1페이지 게시글 개수,

    //검색옵션
    private List<Long> memberSeq; //멤버엔티티의 seq필드?
    private List<String> userId;

    private List<Long> bookCode;

    // 예약일자 범위
    private LocalDate sDate;
    private LocalDate eDate;

}
