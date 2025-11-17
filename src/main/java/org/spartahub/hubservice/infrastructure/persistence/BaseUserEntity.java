package org.spartahub.hubservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@Access(AccessType.FIELD)
@EntityListeners(AuditingEntityListener.class)
public class BaseUserEntity extends BaseEntity{
    @CreatedBy
    @Column(length=45, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(length=45, insertable = false)
    private String modifiedBy;

    @Column(length=45, insertable = false)
    private String deletedBy;

    protected  void updateDelete(String deletedBy) {
        updateDelete();
        this.deletedBy = deletedBy;
    }
}
