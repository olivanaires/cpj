package br.com.ota.cpjbackend.model.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"paymentValue", "description", "payed"})
public class PaymentResponse implements Comparable<PaymentResponse> {

    private String description;

    private LocalDate date;

    private BigDecimal paymentValue;

    private boolean payed;

    @Override
    public int compareTo(PaymentResponse o) {
        return this.date.compareTo(o.date);
    }
}
