package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.annotation.Conditional;
import br.com.ota.cpjbackend.model.enums.ClientType;
import br.com.ota.cpjbackend.model.enums.MaritalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "customers")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String clientName;

    @Size(max = 50)
    private String contactName;

    @Size(max = 20)
    private String identityNumber;

    @Size(min = 14, max = 18)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClientType clientType;

    @Size(max = 50)
    private String contactPhoneOne;

    @Size(max = 50)
    private String contactPhoneTwo;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private MaritalStatus maritalStatus;

    @Size(max = 50)
    private String citizenship;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Address address;

}
