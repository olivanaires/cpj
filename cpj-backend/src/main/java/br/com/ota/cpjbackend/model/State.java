package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "states")
@NoArgsConstructor
public class State extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 2)
    private String acronym;

}
