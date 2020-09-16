package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Expense;
import br.com.ota.cpjbackend.model.Honorary;
import br.com.ota.cpjbackend.model.Payment;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import br.com.ota.cpjbackend.model.vo.PaymentRequest;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.ExpenseRepository;
import br.com.ota.cpjbackend.repository.HonoraryRepository;
import br.com.ota.cpjbackend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;
    private final MessagePropertie messagePropertie;
    private final HonoraryRepository honoraryRepository;
    private final ExpenseService expenseService;

    public void save(PaymentRequest paymentRequest) throws AppException {
        Contract contract = contractRepository.findById(paymentRequest.getContractId())
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));

        switch (paymentRequest.getPaymentType()) {
            case HONORARY:
                Honorary honorary = honoraryRepository.findById(paymentRequest.getId())
                        .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.honorary")));
                honorary.setPayed(true);
                honoraryRepository.save(honorary);
                break;
            case EXPENSE:
                Expense expense = expenseService.load(paymentRequest.getId());
                expense.setPayed(true);
                expenseService.save(expense);
                break;
            default:
                final Payment payment = new Payment(paymentRequest, contract);
                paymentRepository.save(payment);
                break;
        }

    }
}
