package com.dictionary.iam;

import com.dictionary.common.DomainContextProvider;
import com.dictionary.common.DomainEntity;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.UUID;

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
@ToString(exclude = {"rawPassword", "password"})
public class User extends DomainEntity {
    @Column
    private final String userName;

    @Column
    private String password;

    @Column
    private String rawPassword;

    @Enumerated(EnumType.STRING)
    @Column
    @Getter(value = AccessLevel.NONE)
    private UserRole role;

    public GrantedAuthority getRole() {
        return new SimpleGrantedAuthority(role.name());
    }

    public UserRole getUserRole() {
        return role;
    }

    public static User create(UserRole role) {
        User user = User.builder()
                .role(role)
                .userName(UUID.randomUUID().toString())
                .rawPassword(UUID.randomUUID().toString())
                .build();
        user.changePassword(user.getRawPassword());
        return user;
    }

    private void changePassword(String rawPassword) {
        this.password = generatePassword(rawPassword);
    }

    private String generatePassword(String rawPassword) {
        PasswordEncoder passwordEncoder = DomainContextProvider.getApplicationContext().getBean(PasswordEncoder.class);
        return passwordEncoder.encode(rawPassword);
    }
}
