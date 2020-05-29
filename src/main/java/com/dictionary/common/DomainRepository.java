package com.dictionary.common;

import com.dictionary.domain.HttpStatus;
import com.dictionary.domain.exception.EntityNotFoundException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.*;
import java.util.function.Supplier;

@NoRepositoryBean
public interface DomainRepository<ENTITY extends DomainEntity, ID extends Long> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY> {

    Optional<ENTITY> findById(Long id);

    default ENTITY getById(Long id) {
        return findById(id).orElseThrow(entityNotFound());
    }

    Optional<ENTITY> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

    default ENTITY getActiveById(Long id) {
        return findByIdAndEntityStatus(id, EntityStatus.ACTIVE).orElseThrow(entityNotFound());
    }

    Optional<ENTITY> findByGuid(UUID guid);

    Optional<ENTITY> findByGuidAndEntityStatus(UUID guid, EntityStatus entityStatus);

    default ENTITY getByGuid(UUID guid) {
        return findByGuid(guid).orElseThrow(entityNotFound());
    }

    default ENTITY getActiveByGuid(UUID guid) {
        return findByGuidAndEntityStatus(guid, EntityStatus.ACTIVE).orElseThrow(entityNotFound());
    }

    default ENTITY getDeletedByGuid(UUID guid) {
        return findByGuidAndEntityStatus(guid, EntityStatus.DELETED).orElseThrow(entityNotFound());
    }

    default void activate(ENTITY entity) {
        if (EntityStatus.DELETED.equals(entity.getEntityStatus())) {
            entity.activate();
            save(entity);
        }
    }

    default void activate(UUID guid) {
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
        ENTITY entity = getActiveById(id);
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

    default Supplier<EntityNotFoundException> entityNotFound() {
        return () -> {
            Map<String, Object> params = new HashMap<>();
            params.put("entity", getEntityClassName());
            return new EntityNotFoundException(HttpStatus.BAD_REQUEST, null, params);
        };
    }
}
