package com.dictionary.admin.converter;


import com.dictionary.admin.dto.UserDTO;
import com.dictionary.common.BaseConverter;
import com.dictionary.iam.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class UserDTOConverter extends BaseConverter<User, UserDTO> {

    @Override
    public UserDTO convert(User source) {
        return UserDTO.builder()
                .userName(source.getUserName())
                .rawPassword(source.getPassword())
                .entityStatus(source.getEntityStatus())
                .role(source.getUserRole())
                .id(source.getGuid())
                .build();
    }
}
