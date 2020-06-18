package br.com.ota.cpjbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "lawyers")
@NoArgsConstructor
public class Lawyer extends BaseEntity {

    @NotBlank
    @Size(max = 40)
    private String name;

    @Size(max = 10)
    private String oabNumber;

    @OneToOne
    private User user;

}
