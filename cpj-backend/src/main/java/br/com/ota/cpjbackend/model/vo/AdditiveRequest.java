package br.com.ota.cpjbackend.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AdditiveRequest {

    private Date signatureDate;

    private Integer duration;

    private String contractId;

    private BigDecimal paymentValue;

    private Date endDate;

}
