package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "adresses")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String street;

    @NotBlank
    @Size(max = 50)
    private String neighborhood;

    @Size(max = 50)
    private String complement;

    @NotNull
    private String number;

    @NotBlank
    @Size(max = 9)
    private String postalCode;

    @NotBlank
    @Size(max = 50)
    private String city;

}
