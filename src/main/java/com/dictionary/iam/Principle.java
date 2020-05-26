package com.dictionary.iam;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
public class Principle {
    private String clientId;
    private String remoteAddress;
    private String username;
    private boolean authenticated;
}
