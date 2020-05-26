package com.dictionary.common;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.*;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@NoRepositoryBean
public interface DomainRepository<ENTITY extends DomainEntity, ID extends Long> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY> {

    Optional<ENTITY> findById(Long id);

    default ENTITY getById(Long id) throws Exception {
        return findById(id).orElseThrow(entityNotFound());
    }

    Optional<ENTITY> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

    default ENTITY getActiveById(Long id) throws Exception {
        return findByIdAndEntityStatus(id, EntityStatus.ACTIVE).orElseThrow(entityNotFound());
    }

    Optional<ENTITY> findByGuid(UUID guid);

    Optional<ENTITY> findByGuidAndEntityStatus(UUID guid, EntityStatus entityStatus);

    default ENTITY getByGuid(UUID guid) throws Exception {
        return findByGuid(guid).orElseThrow(entityNotFound());
    }

    default ENTITY getActiveByGuid(UUID guid) throws Exception {
        return findByGuidAndEntityStatus(guid, EntityStatus.ACTIVE).orElseThrow(entityNotFound());
    }

    default ENTITY getDeletedByGuid(UUID guid) throws Exception {
        return findByGuidAndEntityStatus(guid, EntityStatus.DELETED).orElseThrow(entityNotFound());
    }

    default void activate(ENTITY entity) {
        if (EntityStatus.DELETED.equals(entity.getEntityStatus())) {
            entity.activate();
            save(entity);
        }
    }

    default void activate(UUID guid) throws Exception {
        ENTITY entity = getByGuid(guid);
        activate(entity);
    }

    @Override
    default void delete(ENTITY entity) {
        entity.delete();
        save(entity);
    }

    @Override
    default void deleteById(ID id) {
        ENTITY entity = null;
        try {
            entity = getActiveById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        delete(entity);
    }

    List<ENTITY> findAllByEntityStatus(EntityStatus entityStatus);

    default List<ENTITY> getAllActive() {
        return findAllByEntityStatus(EntityStatus.ACTIVE);
    }

    default String getEntityClassName() {
        Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(getClass(), DomainRepository.class);
        return classes[0].getSimpleName();
    }

    default Supplier<Exception> entityNotFound() {//todo
        return () -> {
            Map<String, Object> params = new HashMap<>();
            params.put("entity", getEntityClassName());
//            Exception exception = new Exception(BAD_REQUEST, null, params);
            return null;
        };
    }
}
