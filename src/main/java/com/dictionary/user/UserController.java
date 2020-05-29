package com.dictionary.user;


import com.dictionary.common.UserApi;
import com.dictionary.iam.CurrentPrinciple;
import com.dictionary.iam.Principle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Api(tags = {"User"})
@RestController
@RequestMapping("/User")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class UserController {

    private final CurrentPrinciple currentPrinciple;

    @GetMapping("/me")
    @UserApi
    @ApiOperation(value = "Get current User principal", notes = "Permission : USER ")
    public ResponseEntity<Principle> me() {
        return ResponseEntity.ok(currentPrinciple.getPrinciple());
    }
}
