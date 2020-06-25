package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.enums.ClientType;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final MessagePropertie messagePropertie;
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Client client) {
        if (ClientType.PF.equals(client.getClientType()) && clientRepository.existsByCpf(client.getCpf())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("client.exist", client.getCpf())));
        }

        if (ClientType.PJ.equals(client.getClientType()) && clientRepository.existsByCnpj(client.getCnpj())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("client.exist", client.getCnpj())));
        }

        clientRepository.save(client);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.client")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Client> customers = clientRepository.findAll(Sort.by("clientName"));
        return ResponseEntity.ok(customers);
    }

}
