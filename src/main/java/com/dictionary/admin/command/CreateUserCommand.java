package com.dictionary.admin.command;

import com.dictionary.iam.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand {
    @NotNull
    private UserRole userRole;
}
