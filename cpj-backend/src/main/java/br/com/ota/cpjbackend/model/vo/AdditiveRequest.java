package br.com.ota.cpjbackend.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AdditiveRequest {

    private LocalDate signatureDate;

    private Integer duration;

    private String contractId;

    private BigDecimal paymentValue;

    private LocalDate endDate;

}
