package com.dictionary.common;


import com.dictionary.iam.User;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Audited
@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@ToString(callSuper = false, exclude = {"createdBy", "updatedBy", "createdAt", "updatedAt"})
public abstract class DomainEntity {

    private final UUID guid = UUID.randomUUID();
    @NotAudited
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_sequence_generator")
    @Column(name = "id")
    private Long id;
    @Version
    @NotAudited
    private Long version;

    @Audited
    @CreatedDate
    private LocalDateTime createdAt;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;


    @Enumerated(EnumType.STRING)
    private EntityStatus entityStatus = EntityStatus.ACTIVE;

    public void delete() {
        if (EntityStatus.ACTIVE.equals(getEntityStatus())) {
            this.entityStatus = EntityStatus.DELETED;
        }
    }

    public void activate() {
        if (EntityStatus.DELETED.equals(getEntityStatus())) {
            this.entityStatus = EntityStatus.ACTIVE;
        }
    }
}
