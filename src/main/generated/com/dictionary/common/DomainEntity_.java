package com.dictionary.common;

import com.dictionary.iam.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DomainEntity.class)
public abstract class DomainEntity_ {

	public static volatile SingularAttribute<DomainEntity, LocalDateTime> createdAt;
	public static volatile SingularAttribute<DomainEntity, User> updatedBy;
	public static volatile SingularAttribute<DomainEntity, User> createdBy;
	public static volatile SingularAttribute<DomainEntity, EntityStatus> entityStatus;
	public static volatile SingularAttribute<DomainEntity, UUID> guid;
	public static volatile SingularAttribute<DomainEntity, Long> id;
	public static volatile SingularAttribute<DomainEntity, Long> version;
	public static volatile SingularAttribute<DomainEntity, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String UPDATED_BY = "updatedBy";
	public static final String CREATED_BY = "createdBy";
	public static final String ENTITY_STATUS = "entityStatus";
	public static final String GUID = "guid";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String UPDATED_AT = "updatedAt";

}

