package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.Expense;
import br.com.ota.cpjbackend.model.Honorary;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"paymentValue", "description", "payed"})
public class PaymentResponse implements Comparable<PaymentResponse> {

    private Long id;

    private String description;

    private LocalDate date;

    private BigDecimal paymentValue;

    private PaymentType paymentType;

    private boolean payed;

    public PaymentResponse(Honorary honorary) {
        this.id = honorary.getId();
        this.description = honorary.getDescription() + " (Honorário)";
        this.date = honorary.getDate();
        this.paymentValue = honorary.getPaymentValue();
        this.payed = honorary.isPayed();
        this.paymentType = PaymentType.HONORARY;
    }

    public PaymentResponse(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.date = expense.getDate();
        this.paymentValue = expense.getPaymentValue();
        this.payed = expense.isPayed();
        this.paymentType = PaymentType.EXPENSE;
    }

    @Override
    public int compareTo(PaymentResponse o) {
        return this.date.compareTo(o.date);
    }
}
