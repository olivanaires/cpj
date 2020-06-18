package br.com.ota.cpjbackend.model.vo;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @Getter private String username;

    @NotBlank
    @Getter private String password;

}
