package org.sbr.configcontrol.controller;

import lombok.RequiredArgsConstructor;
import org.sbr.configcontrol.model.UserDto;
import org.sbr.configcontrol.security.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;
    @PostMapping("/jwt")
    public String login(@RequestBody UserDto user) {
        return jwtService.authenticate(user);
    }

}
