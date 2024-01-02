package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entities.Base;
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
}
