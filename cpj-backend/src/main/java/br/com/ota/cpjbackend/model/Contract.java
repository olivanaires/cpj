package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.enums.DurationType;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "contracts")
@NoArgsConstructor
public class Contract extends BaseEntity {


    @Size(max = 200)
    private String description;

    @NotNull
    private Date signatureDate;

    @NotNull
    private DurationType durationType;

    @NotNull
    private Integer duration;

    @NotEmpty
    @OneToMany
    private List<Lawyer> hired;

    @NotEmpty
    @OneToMany
    private List<Client> contractors;

    @NotNull
    @ElementCollection(targetClass = PaymentType.class)
    @Enumerated(EnumType.STRING)
    private List<PaymentType> paymentTypes;

    @NotNull
    private BigDecimal paymentValue;

    private BigDecimal entryValue;

    private BigDecimal endPercentValue;

    @OneToMany
    private List<Expense> expenses;

}