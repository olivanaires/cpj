package br.com.ota.cpjbackend.model;

import br.com.ota.cpjbackend.model.vo.PaymentRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {

    @NotBlank
    @Size(max = 200)
    private String description;

    private LocalDate date;

    @NotNull
    private BigDecimal paymentValue;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contract contract;

    public Payment(PaymentRequest paymentRequest, Contract contract) {
        this.description = paymentRequest.getDescription();
        this.date = paymentRequest.getDate();
        this.paymentValue = paymentRequest.getPaymentValue();
        this.contract = contract;
    }

}
