package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Member extends Base {
    //로그인할 때
    @Id @GeneratedValue //PK 자동생성 어노테이션
    private Long seq;

    @Column(length=80,nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    //순환참조방지
    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY) //연관관계의 주인은 member ,필요할 때만 조회하겠다.
    private List<Authorities> authorities = new ArrayList<>();
}
