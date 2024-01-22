package org.choongang.member.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

//게시글 조회관련 엔티티
//게시글 ID와 브라우저 정보로 유니크하게 만들 것임
@Entity
@Data
public class BoardView {
    @Id
    private Long seq; //게시글 번호
    @Id
    private int uid; //회원번호 /오라클 내에선 uid가 예약어라 오류날 수도 있음...


}
