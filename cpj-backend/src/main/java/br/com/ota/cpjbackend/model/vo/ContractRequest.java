package br.com.ota.cpjbackend.model.vo;

import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.enums.ContractType;
import br.com.ota.cpjbackend.model.enums.DurationType;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ContractRequest {

    private Long id;

    private String number;

    private ContractType description;

    private LocalDate signatureDate;

    private LocalDate signatureEndDate;

    private LocalDate endDate;

    private LocalDate paymentDate;

    private DurationType durationType;

    private Integer duration;

    private BigDecimal paymentSignatureValue;

    private BigDecimal paymentValue;

    private BigDecimal entryValue;

    private BigDecimal endPercentValue;

    private List<PaymentType> paymentTypes;

    private PaymentType paymentType;

    private List<String> hired;

    private List<String> contractors;

    private String observations;

    public Contract toContract(Contract contract) {
        contract.setNumber(this.number);
        contract.setDescription(this.description);
        contract.setSignatureDate(this.signatureDate);
        contract.setSignatureEndDate(this.signatureEndDate);
        contract.setDurationType(this.durationType);
        contract.setDuration(this.duration);
        contract.setPaymentValue(this.paymentValue);
        contract.setPaymentSignatureValue(this.paymentSignatureValue);
        contract.setEntryValue(this.entryValue);
        contract.setEndPercentValue(this.endPercentValue);
        contract.setEndDate(this.endDate);
        contract.setPaymentDate(this.paymentDate);
        contract.setPaymentType(this.paymentType);
        contract.setObservations(this.observations);
        return contract;
    }

}
