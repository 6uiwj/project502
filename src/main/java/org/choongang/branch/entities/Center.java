package org.choongang.branch.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.commons.entities.BaseMember;

import java.util.List;
//관리자가 등록할거니까 등록한 관리자가 누군지 입력할 수 있게 상속
@Data
@Entity
public class Center extends BaseMember {

    @Column(length=80, nullable = false)
    private String cName; // 지점명

    @Column(length=10, nullable = false)
    private String zonecode; // 우편번호

    @Column(length=100, nullable = false)
    private String address; // 주소

    private String addressSub; // 나머지 주소

    @Column(length=15, nullable = false)
    private String telNum; // 전화번호

    @Column(length=80, nullable = false)
    private String operHour; // 운영시간


    /* ㅡㅡㅡㅡㅡㅡㅡ예약과 관련된 설정 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    //월, 화, 수, 목 ... -> 문자로 쪼개서 넣기
    //월||화||수  등..알아서 쪼개.
    @Column(length=40)
    private List<String> bookYoil; //  예약 요일 설정
    //예약가능시간은 시작시간 종료시간 합쳐서 bookavltime으로 넣어줄 것임

    //나중에 중간에 - 기준으로 잘라서 쓰자
    @Column(length=20)
    private String bookAvl; // 예약 가능 시간 09:00-18:00 (Stime+Etime 합쳐서 넣기)

    /**
     * 13:00-14:00
     * 15:30-16:00 이런식으로 쪼개서 쓸 생각
     */
    @Lob //여러개 입력할거니까 줄개행해서 여러개 입력받도록
    private String bookNotAvl; // 예약 불가 시간


    private boolean bookHday; // 공휴일 예약 가능 여부


    private int bookBlock; // 예약 블록 10분 단위인지, 20분단위인지, 30

    private int bookCapacity; // -1이면 무제한, 0이면 예약 불가
}
