package com.dictionary.domain.notification.email;


import com.dictionary.common.DomainEntity;
import com.dictionary.common.EmailAddress;
import lombok.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity
@ToString
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(allocationSize = 1, name = "app_sequence_generator", sequenceName = "seq_email")
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@Table(name = "\"email\"",
        indexes = {
                @Index(name = "email_status_idx", columnList = "status"),
                @Index(name = "email_guid_uq_idx", columnList = "guid", unique = true)
        })
public class Email extends DomainEntity {

    @Enumerated(EnumType.STRING)
    private final EmailType type;

    @Enumerated(EnumType.STRING)
    EmailStatus status = EmailStatus.WAITING;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "\"to\"")
    private final String emailTo;

    private final String subject;

    private final String senderName;

    private final String language;

    public Email(EmailType type, List<EmailAddress> toList, String subject, String senderName, String language) {
        this.type = type;
        this.emailTo = convertToCommaSeparatedString(toList);
        this.subject = subject;
        this.senderName = senderName;
        this.language = language;
    }

    private String convertToCommaSeparatedString(List<EmailAddress> emailAddressList) {
        if (CollectionUtils.isEmpty(emailAddressList)) {
            return null;
        }

        String commaSeparatedString = emailAddressList.stream()
                .filter(Objects::nonNull)
                .map(s -> s.getValue()).collect(Collectors.joining(","));

        return "".equals(commaSeparatedString) ? null : commaSeparatedString;
    }

    public List<EmailAddress> getTo() {
        if (StringUtils.isEmpty(this.emailTo))
            return null;
        String[] items = this.emailTo.split(",");
        return Arrays.stream(items).map(EmailAddress::parse).collect(Collectors.toList());
    }

    public void failed() {
        this.status = EmailStatus.FAILED;
    }

    public void succeed() {
        this.status = EmailStatus.SUCCESSFUL;
    }
}
