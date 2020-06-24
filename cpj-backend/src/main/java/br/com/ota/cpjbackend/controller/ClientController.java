package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.enums.ClientType;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final MessagePropertie messagePropertie;
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Client client) {
        if (ClientType.PF.equals(client.getClientType()) && clientRepository.existsByCpf(client.getCpf())) {
            return ResponseEntity.badRequest()
                    .body(new AppException(messagePropertie.getMessage("client.exist", client.getCpf())));
        }

        if (ClientType.PJ.equals(client.getClientType()) && clientRepository.existsByCnpj(client.getCnpj())) {
            return ResponseEntity.badRequest()
                    .body(new AppException(messagePropertie.getMessage("client.exist", client.getCnpj())));
        }

        clientRepository.save(client);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.client")));
    }
}
