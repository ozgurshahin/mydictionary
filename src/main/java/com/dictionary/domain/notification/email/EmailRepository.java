package com.dictionary.domain.notification.email;

import com.dictionary.common.DomainRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends DomainRepository<Email, Long> {
}
