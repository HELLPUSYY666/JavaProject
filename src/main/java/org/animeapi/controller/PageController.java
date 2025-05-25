package org.animeapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.animeapi.dto.PasswordDto;
import org.animeapi.model.MyUser;
import org.animeapi.repository.UserRepository;
import org.animeapi.response.GenericResponse;
import org.animeapi.service.EmailService;
import org.animeapi.service.SecurityService;
import org.animeapi.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PageController {
    private final SecurityService securityService;
    private JavaMailSender mailSender;
    private final UserService userService;
    private UserRepository userRepository;
    private EmailService emailService;
    private MessageSource messageSource;

    private String getAppUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.substring(0, url.indexOf(request.getRequestURI()));
    }

    public PageController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/req/signup")
    public String registerUser(MyUser user) {
        userRepository.save(user);
        return "redirect:/index";
    }

    @PostMapping("/req/login")
    public String enterIndex(MyUser user){
        userRepository.findUserByEmail(user.getEmail());
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/user/changePassword")
    public String showChangePasswordPage(Locale locale, Model model,
                                         @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(token);
        if(result != null) {
            String message = messageSource.getMessage("auth.message." + result, null, locale);
            return "redirect:/login.html?lang="
                    + locale.getLanguage() + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        }
    }

    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {

        String result = securityService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(messageSource.getMessage(
                    "auth.message." + result, null, locale));
        }

        Optional<MyUser> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse(messageSource.getMessage(
                    "message.resetPasswordSuc", null, locale));
        } else {
            return new GenericResponse(messageSource.getMessage(
                    "auth.message.invalid", null, locale));
        }
    }
}
