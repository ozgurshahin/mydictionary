package com.dictionary.admin.dto;


import com.dictionary.common.EntityStatus;
import com.dictionary.iam.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userName;
    private String rawPassword;
    private EntityStatus entityStatus;
    private UserRole role;
    private UUID id;
}
