package com.dictionary.admin;

import com.dictionary.common.AdminApi;
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


//@Api(tags = {"Operator"})
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class OperatorController {

    private final CurrentPrinciple currentPrinciple;

    @GetMapping("/me")
//    @AdminApi
    @ApiOperation(value = "Get current admin principal", notes = "Permission : OPERATOR ")
    public ResponseEntity<Principle> me() {
        return ResponseEntity.ok(currentPrinciple.getPrinciple());
    }
}
