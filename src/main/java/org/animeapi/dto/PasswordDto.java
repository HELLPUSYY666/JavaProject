package org.animeapi.dto;


import jakarta.validation.constraints.NotEmpty;

public class PasswordDto {

    @NotEmpty(message = "Token is required")
    private String token;

    @NotEmpty(message = "New password is required")
    private String newPassword;

    // (опционально) подтверждение пароля
    private String confirmPassword;

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
