package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by @author olivanaires on 11/05/2019.
 */
@Getter
@Setter
public class UserRequest {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    private RoleName role;

}
