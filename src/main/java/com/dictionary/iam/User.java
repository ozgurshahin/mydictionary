package com.dictionary.iam;

import com.dictionary.common.DomainEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Audited
@Getter
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@SequenceGenerator(allocationSize = 1, name = "app_sequence_generator", sequenceName = "seq_user")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"",
        indexes = {
                @Index(name = "user_username_uq_idx", columnList = "username", unique = true)
        })
public class User extends DomainEntity {
    @Column
    private final String userName;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    @Getter(value = AccessLevel.NONE)
    private UserRole role;

    public GrantedAuthority getRole() {
        return new SimpleGrantedAuthority(role.name());
    }

}
