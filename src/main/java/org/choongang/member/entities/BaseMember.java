package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.choongang.commons.entities.Base;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base { //날짜,시간 + 회원정보

    @CreatedBy
    @Column(length=40, updatable = false)
    private String createdBy;


    @LastModifiedBy
    @Column(length=40, insertable = false)
    private String modifiedBy;
}
