package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Additive;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.enums.ContractType;
import br.com.ota.cpjbackend.model.vo.*;
import br.com.ota.cpjbackend.repository.FileRepository;
import br.com.ota.cpjbackend.service.AdditiveService;
import br.com.ota.cpjbackend.service.ContractService;
import br.com.ota.cpjbackend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {

    private final MessagePropertie messagePropertie;
    private final ContractService contractService;
    private final AdditiveService additiveService;
    private final FileRepository fileRepository;
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest contractRequest) {

        if (ContractType.LEGAL_ADVICE.equals(contractRequest.getDescription()) && (Objects.isNull(contractRequest.getEndDate()) || Objects.isNull(contractRequest.getSignatureEndDate()))) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.error.required", "model.contract.endDate")));
        }

        try {
            Long contractId = contractService.create(contractRequest);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.contract"), contractId));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.error")));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Contract> all = contractService.list();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Contract contract = contractService.load(id);
            return ResponseEntity.ok(contract);
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {

            List<Additive> additives = additiveService.fidByContractId(id);
            for (Additive a : additives) {
                additiveService.remove(a.getId());
            }

            List<FileResponse> files = fileRepository.findAllByContractId(id);
            for (FileResponse file : files) {
                fileRepository.deleteById(file.getId());
            }

            contractService.remove(id);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.deleted.success", "model.contract")));
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

    @GetMapping("/list/payments/{id}")
    public ResponseEntity<?> contractPayments(@PathVariable String id) {
        try {
            Set<PaymentResponse> contracts = contractService.contractPayments(id);
            return ResponseEntity.ok(contracts);
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

    @GetMapping("/list/with-payment-this-month")
    public ResponseEntity<?> contractsWithPaymentForCurrentlyMonth() {
        List<Contract> contracts = contractService.contractsWithPaymentForCurrentlyMonth();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/list/with-end-date-this-month")
    public ResponseEntity<?> findAllWithEndDateForCurrentlyMonth() {
        List<Contract> contracts = contractService.findAllWithEndDateForCurrentlyMonth();
        return ResponseEntity.ok(contracts);
    }

    @PostMapping("/receve-payment")
    public ResponseEntity<?> receivePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            paymentService.save(paymentRequest);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.payment")));
        } catch (AppException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

}
