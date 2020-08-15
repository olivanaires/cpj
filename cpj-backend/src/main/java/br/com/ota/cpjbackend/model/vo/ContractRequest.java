package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.enums.ContractType;
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

    private Long id;

    private String number;

    private ContractType description;

    private Date signatureDate;

    private Date endDate;

    private Date paymentDate;

    private DurationType durationType;

    private Integer duration;

    private BigDecimal paymentValue;

    private BigDecimal entryValue;

    private BigDecimal endPercentValue;

    private List<PaymentType> paymentTypes;

    private PaymentType paymentType;

    private List<String> hired;

    private List<String> contractors;

    private String observations;

    public Contract toContract(Contract contract) {
        contract.setNumber(this.getNumber());
        contract.setDescription(this.getDescription());
        contract.setSignatureDate(this.getSignatureDate());
        contract.setDurationType(this.getDurationType());
        contract.setDuration(this.getDuration());
        contract.setPaymentValue(this.getPaymentValue());
        contract.setEntryValue(this.getEntryValue());
        contract.setEndPercentValue(this.getEndPercentValue());
        contract.setEndDate(this.endDate);
        contract.setPaymentDate(this.paymentDate);
        contract.setPaymentType(this.paymentType);
        contract.setObservations(this.observations);
        return contract;
    }

}
