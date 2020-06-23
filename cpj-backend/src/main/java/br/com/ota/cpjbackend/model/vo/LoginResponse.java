package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {

    private User user;

    private String token;

}
