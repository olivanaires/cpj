package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.enums.ContractType;
import br.com.ota.cpjbackend.model.enums.DurationType;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "contracts")
@NoArgsConstructor
public class Contract extends BaseEntity {


    @NotBlank
    @Size(max = 50)
    private String number;

    @NotNull
    private ContractType description;

    @NotNull
    private Date signatureDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Date paymentDate;

    @NotNull
    private DurationType durationType;

    @NotNull
    private Integer duration;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contracts_hired", joinColumns = @JoinColumn(name = "contract_id"), inverseJoinColumns = @JoinColumn(name = "hired_id"))
    private Set<Lawyer> hired;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contracts_contractors", joinColumns = @JoinColumn(name = "contract_id"), inverseJoinColumns = @JoinColumn(name = "contractors_id"))
    private Set<Client> contractors;

//    @NotNull
//    @ElementCollection(targetClass = PaymentType.class)
//    @Enumerated(EnumType.STRING)
//    private List<PaymentType> paymentTypes;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private BigDecimal paymentValue;

    private BigDecimal entryValue;

    private BigDecimal endPercentValue;

    private String observations;

}
