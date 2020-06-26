package br.com.ota.cpjbackend.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LawyerRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String oabNumber;

    @NotNull
    private UserRequest user;

}
