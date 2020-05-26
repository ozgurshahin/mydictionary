package com.dictionary.iam;

import com.dictionary.common.DomainRepository;
import com.dictionary.common.EntityStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends DomainRepository<User, Long> {

    Optional<User> findByUserNameAndEntityStatus(String userName, EntityStatus status);


    @Transactional(propagation = Propagation.REQUIRES_NEW)//todo
    default Optional<User> getByActiveUserInNewTrn(String username) {
        return findByUserNameAndEntityStatus(username, EntityStatus.ACTIVE);
    }

    default User getUser(String userName) throws Exception {//todo
        return findByUserNameAndEntityStatus(userName, EntityStatus.ACTIVE)
                .orElseThrow(Exception::new);
    }
}
