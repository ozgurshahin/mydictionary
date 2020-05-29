package com.dictionary.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorResponse {
    private String code;
    private String message;
    private List<FieldErrorDTO> validationMessages;
    @JsonIgnore
    private String trace;
}
