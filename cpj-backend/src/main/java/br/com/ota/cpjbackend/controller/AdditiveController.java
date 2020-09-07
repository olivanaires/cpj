package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Additive;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.vo.AdditiveRequest;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.service.AdditiveService;
import br.com.ota.cpjbackend.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/additive")
@RequiredArgsConstructor
public class AdditiveController {

    private final MessagePropertie messagePropertie;
    private final AdditiveService additiveService;
    private final ContractService contractService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody AdditiveRequest additiveRequest) {

        try {
            Contract contract = contractService.load(additiveRequest.getContractId());
            contract.setEndDate(additiveRequest.getEndDate());
            contract.setPaymentValue(additiveRequest.getPaymentValue());
            contractService.update(contract);

            Additive additive = new Additive();
            additive.setDuration(additiveRequest.getDuration());
            additive.setPaymentValue(additiveRequest.getPaymentValue());
            additive.setSignatureDate(additiveRequest.getSignatureDate());
            additive.setSignatureEndDate(additiveRequest.getEndDate());
            additive.setContract(contract);
            additiveService.create(additive);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.additive")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.error")));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Additive> all = additiveService.list();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Additive additive = additiveService.load(id);
            return ResponseEntity.ok(additive);
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

    @GetMapping("/loadByContract/{id}")
    public ResponseEntity<?> loadByContract(@PathVariable String id) {
        List<Additive> additives = additiveService.fidByContractId(Long.parseLong(id));
        return ResponseEntity.ok(additives);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            additiveService.remove(id);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.deleted.success", "model.additive")));
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }
}
