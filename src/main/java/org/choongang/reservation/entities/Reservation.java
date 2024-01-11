package org.choongang.reservation.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.branch.entities.Center;
import org.choongang.commons.entities.Base;
import org.choongang.member.entities.Member;
import org.choongang.reservation.constants.DonationType;

import java.time.LocalDateTime;

@Entity
@Data
//날짜와시간이 필요하므로 상속
public class Reservation extends Base {
    @Id @GeneratedValue
    private Long bookCode; //예약 코드

    //한명이 여러군데에 예약할 수있으니까...
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="member_seq")
    private Member member;

    @Column(length=15, nullable = false)
    private String donnerTel; //헌혈자 전화번호 /로그인화면이면 완성시키고 ,바꿀 수있도록 설정

    //헌혈타입은 상수로 빼준다.
    @Enumerated(EnumType.STRING)
    @Column(length=25, nullable = false)
    private DonationType bookType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cCode")
    private Center center;

    private LocalDateTime bookDateTime;
}