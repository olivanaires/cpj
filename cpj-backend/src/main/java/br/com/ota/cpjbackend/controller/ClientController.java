package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.enums.ClientType;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.ClientRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final MessagePropertie messagePropertie;
    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Client client) {
        clientRepository.save(client);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.client")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Client> customers = clientRepository.findAll(Sort.by("clientName"));
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Client client = clientRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.client")));
            return ResponseEntity.ok(client);
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

}
