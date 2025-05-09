package org.animeapi.service;

import org.animeapi.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailService {

    private final MessageSource messages;
    private final Environment env;

    @Autowired
    public EmailService(MessageSource messages, Environment env) {
        this.messages = messages;
        this.env = env;
    }

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, MyUser user) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, MyUser user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}

