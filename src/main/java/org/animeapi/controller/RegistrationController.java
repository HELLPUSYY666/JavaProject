package org.animeapi.controller;

import lombok.AllArgsConstructor;
import org.animeapi.model.MyUser;
import org.animeapi.request.RegistrationRequest;
import org.animeapi.service.RegistrationService;
import org.animeapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RegistrationService registrationService;
    @PostMapping(value = "/req/signup", consumes = "application/json")
    public String createUser(@RequestBody MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/index";
    }

    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }





}
