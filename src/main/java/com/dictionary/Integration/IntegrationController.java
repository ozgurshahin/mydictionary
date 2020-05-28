//package com.dictionary.Integration;
//
//
//import com.dictionary.common.IntegrationApi;
//import com.dictionary.iam.CurrentPrinciple;
//import com.dictionary.iam.Principle;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.NotNull;
//
//@Api(tags = {"Integration"})
//@RestController
//@RequestMapping("/integration")
//@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
//public class IntegrationController {
//
//    private final CurrentPrinciple currentPrinciple;
//
//    @GetMapping("/me")
//    @IntegrationApi
//    @ApiOperation(value = "Get current integration principal", notes = "Permission : INTEGRATION ")
//    public ResponseEntity<Principle> me() {
//        return ResponseEntity.ok(currentPrinciple.getPrinciple());
//    }
//}
