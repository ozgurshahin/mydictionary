package com.dictionary.iam;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.dictionary.common.DomainEntity_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, UserRole> role;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, String> rawPassword;

	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String USER_NAME = "userName";
	public static final String RAW_PASSWORD = "rawPassword";

}

