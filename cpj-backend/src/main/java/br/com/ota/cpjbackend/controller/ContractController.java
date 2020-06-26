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
    private final ClientRepository clientRepository;
    private final LawyerRepository lawyerRepository;
    private final ContractRepository contractRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest contractRequest) {

        try {
            List<Client> customers = clientRepository.findAllByCpfInOrCnpjIn(contractRequest.getContractors());
            List<Lawyer> lawyers = lawyerRepository.findAllByOabNumberIn(contractRequest.getHired());

            Contract contract = new Contract();
            contract.setNumber(contractRequest.getNumber());
            contract.setDescription(contractRequest.getDescription());
            contract.setSignatureDate(contractRequest.getSignatureDate());
            contract.setDurationType(contractRequest.getDurationType());
            contract.setDuration(contractRequest.getDuration());
            contract.setHired(lawyers);
            contract.setContractors(customers);
            contract.setPaymentTypes(contractRequest.getPaymentTypes());
            contract.setPaymentValue(contractRequest.getPaymentValue());
            contract.setEntryValue(contractRequest.getEntryValue());
            contract.setEndPercentValue(contractRequest.getEndPercentValue());
            contractRepository.save(contract);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.contract")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.error")));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(contractRepository.findAll());
    }
}
