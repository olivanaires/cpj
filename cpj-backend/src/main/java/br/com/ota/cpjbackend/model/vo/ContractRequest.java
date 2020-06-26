package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.enums.DurationType;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContractRequest {

    private String number;

    private String description;

    private Date signatureDate;

    private DurationType durationType;

    private Integer duration;

    private List<String> hired;

    private List<String> contractors;

    private List<PaymentType> paymentTypes;

    private BigDecimal paymentValue;

    private BigDecimal entryValue;

    private BigDecimal endPercentValue;

}
