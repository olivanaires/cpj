package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.*;
import br.com.ota.cpjbackend.model.enums.PaymentType;
import br.com.ota.cpjbackend.model.vo.ContractRequest;
import br.com.ota.cpjbackend.model.vo.PaymentResponse;
import br.com.ota.cpjbackend.repository.*;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final MessagePropertie messagePropertie;
    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final LawyerRepository lawyerRepository;
    private final FileRepository fileRepository;
    private final PaymentRepository paymentRepository;
    private final HonoraryRepository honoraryRepository;

    public Long create(ContractRequest contractRequest) throws AppException {

        try {
            List<Client> customers = clientRepository.findAllByCpfCnpjIn(contractRequest.getContractors());
            List<Lawyer> lawyers = lawyerRepository.findAllByOabNumberIn(contractRequest.getHired());

            Contract contract;
            if (Objects.isNull(contractRequest.getId())) {
                contract = contractRequest.toContract(new Contract());
            } else {
                contract = contractRepository.findById(contractRequest.getId())
                        .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.contract")));
                contractRequest.toContract(contract);
            }

            contract.setHired(new HashSet<>(lawyers));
            contract.setContractors(new HashSet<>(customers));
            Contract entity = contractRepository.save(contract);
            return entity.getId();
        } catch (Exception e) {
            throw new AppException(messagePropertie.getMessage("message.error"));
        }
    }

    public List<Contract> list() {
        return contractRepository.findAll();
    }

    public List<Contract> listWithHonorary() {
        return contractRepository.findAllByPaymentTypeIsIn(PaymentType.HONORARY, PaymentType.MONTHLY_HONORARY, PaymentType.YEARLY_HONORARY);
    }

    public Contract load(String id) throws AppException {
        return contractRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));
    }

    public void remove(Long id) throws AppException {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AppException(messagePropertie.getMessage("message.model.not.found", "model.contract")));

        if (Objects.nonNull(contract.getFileId())) {
            fileRepository.deleteById(contract.getFileId());
        }

        contractRepository.delete(contract);
    }

    public void update(Contract contract) {
        contractRepository.save(contract);
    }

    public Set<PaymentResponse> contractPayments(String contractId) throws AppException {
        Contract contract = load(contractId);

        List<PaymentResponse> paymentsByContract = paymentRepository.findAllByContractId(Long.parseLong(contractId));
        SortedSet<PaymentResponse> payments = new TreeSet<>(paymentsByContract);

        List<PaymentResponse> honoraries = honoraryRepository.findAllPaymentsByContractId(Long.parseLong(contractId));
        payments.addAll(honoraries);

        LocalDate signatureDate = contract.getSignatureDate();
        LocalDate endDate = Objects.nonNull(contract.getSignatureEndDate()) ? contract.getSignatureEndDate() : LocalDate.of(signatureDate.getYear(), 12, signatureDate.getDayOfMonth());
        long qtdPayments;

        PaymentResponse pr;
        if(PaymentType.MONTHLY.equals(contract.getPaymentType()) || PaymentType.MONTHLY_HONORARY.equals(contract.getPaymentType())) {
            qtdPayments = ChronoUnit.MONTHS.between(signatureDate, endDate);
            for (int i = 0; i < qtdPayments; i++) {
                pr = new PaymentResponse(null, messagePropertie.getMessage("message.contract.month.payment", String.valueOf(i + 1)),
                        signatureDate.plusMonths(i),
                        contract.getPaymentSignatureValue(), PaymentType.MONTHLY, false);
                payments.add(pr);
            }
        } else if(PaymentType.YEARLY.equals(contract.getPaymentType()) || PaymentType.YEARLY_HONORARY.equals(contract.getPaymentType())) {
            qtdPayments = ChronoUnit.YEARS.between(signatureDate, endDate);
            for (int i = 0; i < qtdPayments; i++) {
                pr = new PaymentResponse(null, messagePropertie.getMessage("message.contract.yearly.payment", String.valueOf(i + 1)),
                        signatureDate.plusYears(i),
                        contract.getPaymentSignatureValue(), PaymentType.YEARLY, false);
                payments.add(pr);
            }
        }

        if (!CollectionUtils.isEmpty(contract.getAdditives())) {
            generateAdditivesPayments(contract, payments);
        }

        return payments;
    }

    private void generateAdditivesPayments(Contract contract, SortedSet<PaymentResponse> payments) {
        LocalDate signatureDate;
        LocalDate endDate;
        long qtdPayments;
        PaymentResponse pr;
        int count = 0;
        for (Additive additive : contract.getAdditives()) {
            count++;
            signatureDate = additive.getSignatureDate();
            endDate = additive.getSignatureEndDate();

            if(PaymentType.MONTHLY.equals(contract.getPaymentType()) || PaymentType.MONTHLY_HONORARY.equals(contract.getPaymentType())) {
                qtdPayments = ChronoUnit.MONTHS.between(signatureDate, endDate);
                for (int i = 0; i < qtdPayments; i++) {
                    pr = new PaymentResponse(null, messagePropertie.getMessage("message.additive.month.payment", String.valueOf(i + 1), String.valueOf(count)),
                            signatureDate.plusMonths(i),
                            additive.getPaymentValue(), PaymentType.MONTHLY, false);
                    payments.add(pr);
                }
            } else if(PaymentType.YEARLY.equals(contract.getPaymentType()) || PaymentType.YEARLY_HONORARY.equals(contract.getPaymentType())) {
                qtdPayments = ChronoUnit.YEARS.between(signatureDate, endDate);
                for (int i = 0; i < qtdPayments; i++) {
                    pr = new PaymentResponse(null, messagePropertie.getMessage("message.additive.yearly.payment", String.valueOf(i + 1), String.valueOf(count)),
                            signatureDate.plusYears(i),
                            contract.getPaymentSignatureValue(), PaymentType.YEARLY, false);
                    payments.add(pr);
                }
            }
        }
    }

    public List<Contract> contractsWithPaymentForCurrentlyMonth() {
        return contractRepository.findAllWithPaymentForCurrentlyMonth();
    }

    public List<Contract> findAllWithEndDateForCurrentlyMonth() {
        return contractRepository.findAllWithEndDateForCurrentlyMonth();
    }

}
