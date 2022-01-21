package com.ex.memberboard.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 자식 Entity 즉 상속받은 Entity 클래스가 무슨 기능을 수행하는지 감지하는 어노테이션
@Getter
public abstract class BaseEntity {
    // abstract : 추상 클래스

    @CreationTimestamp // create 즉 insert가 수행된 시간
    @Column(updatable = false) // update 할 때 값이 들어가지 않게
    private LocalDateTime createTime; // insert 수행한 시간.

    @UpdateTimestamp // update 즉 update가 수행된 시간
    @Column(insertable = false) // insert 할 때 값이 들어가지 않게
    private LocalDateTime updateTime; // update 수행한 시간.

}