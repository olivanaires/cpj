package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.vo.ContractRequest;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {

    private final MessagePropertie messagePropertie;
    private final ContractService contractService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest contractRequest) {

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
            contractService.remove(id);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.deleted.success", "model.contract")));
        } catch (AppException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

}
