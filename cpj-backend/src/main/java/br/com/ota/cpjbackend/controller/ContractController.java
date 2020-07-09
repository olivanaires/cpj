package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.Contract;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.vo.ContractRequest;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ClientRepository;
import br.com.ota.cpjbackend.repository.ContractRepository;
import br.com.ota.cpjbackend.repository.LawyerRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/contract")
@RequiredArgsConstructor
public class ContractController {

    private final MessagePropertie messagePropertie;
    private final ClientRepository clientRepository;
    private final LawyerRepository lawyerRepository;
    private final ContractRepository contractRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest contractRequest) {

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
            contract.setHired(lawyers);
            contract.setContractors(customers);
            contractRepository.save(contract);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.contract")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.error")));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Contract> all = contractRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Contract contract = contractRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.contract")));
            return ResponseEntity.ok(contract);
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }
}
