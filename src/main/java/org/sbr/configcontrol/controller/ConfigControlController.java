package org.sbr.configcontrol.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
@Slf4j
public class ConfigControlController {

    @GetMapping
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    public String getConfigValue(String id) {
        return "dummy value";
    }
    @PostMapping
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @Secured("ROLE_ADMIN")
    public String postConfigValue(@RequestParam String id) {
        return "dummy value2";
    }


}
