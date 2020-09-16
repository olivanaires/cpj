package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentRequest {

    private Long id;

    private String description;

    private LocalDate date;

    private BigDecimal paymentValue;

    private PaymentType paymentType;

    private Long contractId;

}
