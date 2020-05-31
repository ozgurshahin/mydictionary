package com.dictionary.domain.notification.email;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Email.class)
public abstract class Email_ extends com.dictionary.common.DomainEntity_ {

	public static volatile SingularAttribute<Email, String> senderName;
	public static volatile SingularAttribute<Email, String> subject;
	public static volatile SingularAttribute<Email, String> emailTo;
	public static volatile SingularAttribute<Email, String> language;
	public static volatile SingularAttribute<Email, EmailType> type;
	public static volatile SingularAttribute<Email, EmailStatus> status;

	public static final String SENDER_NAME = "senderName";
	public static final String SUBJECT = "subject";
	public static final String EMAIL_TO = "emailTo";
	public static final String LANGUAGE = "language";
	public static final String TYPE = "type";
	public static final String STATUS = "status";

}

