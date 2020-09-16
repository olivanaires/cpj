package br.com.ota.cpjbackend.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentRequest {

    private String description;

    private LocalDate date;

    private BigDecimal paymentValue;

    private Long contractId;

}
