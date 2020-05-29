package com.dictionary.admin;


import com.dictionary.admin.command.CreateUserCommand;
import com.dictionary.admin.converter.UserDTOConverter;
import com.dictionary.admin.dto.UserDTO;
import com.dictionary.common.AdminApi;
import com.dictionary.common.DomainEvents;
import com.dictionary.domain.UserCreatedEvent;
import com.dictionary.iam.User;
import com.dictionary.iam.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Api(tags = {"User"})
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
@Transactional
public class UserController {

    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;

    @PostMapping("/create")
    @AdminApi
    @ApiOperation(value = "Create User", notes = "Permission : OPERATOR ")
    public UserDTO create(@RequestBody @Valid CreateUserCommand command) {
        User user = User.create(command.getUserRole());
        userRepository.save(user);
        DomainEvents.raise(new UserCreatedEvent(user));
        return userDTOConverter.convert(user);
    }

    @GetMapping("/list")
    @AdminApi
    @ApiOperation(value = "List User", notes = "Permission : OPERATOR ")
    public List<UserDTO> list() {
        return userDTOConverter.convert(userRepository.findAll());
    }

    @PostMapping("/activate/{id}")
    @AdminApi
    @ApiOperation(value = "Activate User", notes = "Permission : OPERATOR ")
    public UserDTO activate(@PathVariable("id") UUID uuid) {
        User user = userRepository.getActiveByGuid(uuid);
        user.activate();
        userRepository.save(user);
        return userDTOConverter.convert(user);
    }

    @PostMapping("/delete/{id}")
    @AdminApi
    @ApiOperation(value = "Delete User", notes = "Permission : OPERATOR ")
    public UserDTO delete(@PathVariable("id") UUID uuid) {
        User user = userRepository.getByGuid(uuid);
        user.delete();
        userRepository.save(user);
        return userDTOConverter.convert(user);
    }
}
