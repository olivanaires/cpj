package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "cities")
@NoArgsConstructor
public class City extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    @ManyToOne
    private State state;

}
