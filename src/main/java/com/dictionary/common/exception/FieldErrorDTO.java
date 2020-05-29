package com.dictionary.common.exception;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@ToString
@Builder
public class FieldErrorDTO {
    private String field;
    private String message;
}
