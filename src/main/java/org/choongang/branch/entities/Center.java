package org.choongang.branch.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import org.choongang.commons.entities.BaseMember;

import java.util.List;
//관리자가 등록할거니까 등록한 관리자가 누군지 입력할 수 있게 상속
public class Center extends BaseMember {

    @Id @GeneratedValue
    private Long cCode; // 지점 코드

    @NotBlank
    private String cName; // 지점명

    @NotBlank
    private String zonecode; // 우편번호

    private String address; // 주소
    private String addressSub; // 나머지 주소

    @Column(length=15, nullable = false)
    private String telNum; // 전화번호

    @Column(length=80, nullable = false)
    private String operHour; // 운영시간

    //월, 화, 수, 목 ... -> 문자로

    @Column(length=40)
    private List<String> bookYoil; //  예약 요일 설정
    //예약가능시간은 시작시간 종료시간 합쳐서 bookavltime으로 넣어줄 것임

    @Column(length=20)
    private String bookAvl; // 예약 가능 시작 시간 09:00-18:00

    /**
     * 13:00-14:00
     * 15:30-16:00 이런식으로 쪼개서 쓸 생각
     */
    @Lob
    private String bookNotAvl; // 예약 불가 시간
    private boolean bookHday; // 공휴일 예약 가능 여부


    private int bookBlock; // 예약 블록 10, 20, 30
    private int bookCapacity; // -1이면 무제한, 0이면 예약 불가
}
