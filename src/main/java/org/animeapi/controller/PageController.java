package org.animeapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.animeapi.customerror.UserNotFoundException;
import org.animeapi.dto.PasswordDto;
import org.animeapi.model.MyUser;
import org.animeapi.response.GenericResponse;
import org.animeapi.service.EmailService;
import org.animeapi.service.SecurityService;
import org.animeapi.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PageController {
    private final SecurityService securityService;
    private JavaMailSender mailSender;
    private final UserService userService;
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
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        MyUser user = userService.findUserByEmail(userEmail);
        if (user.getUserId() == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(emailService.constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messageSource.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
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
