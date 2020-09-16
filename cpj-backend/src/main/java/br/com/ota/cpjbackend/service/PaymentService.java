package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Payment;
import br.com.ota.cpjbackend.model.vo.PaymentRequest;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;
    private final MessagePropertie messagePropertie;

    public void save(PaymentRequest paymentRequest) throws AppException {
        Contract contract = contractRepository.findById(paymentRequest.getContractId())
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));

        final Payment payment = new Payment(paymentRequest, contract);
        paymentRepository.save(payment);
    }
}
