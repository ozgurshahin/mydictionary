package com.dictionary.domain.notification.email;

public enum EmailType {

    // OPERATOR
    OPERATOR_CREATE("operator/create_operator"),
    OPERATOR_RESET_PASSWORD("operator/reset_password"),

    // AGENT
    AGENT_CLIENT_CREATE("agent/create_agent_client"),
    AGENT_USER_CREATE("agent/create_agent_user");

    private String template;

    EmailType(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
