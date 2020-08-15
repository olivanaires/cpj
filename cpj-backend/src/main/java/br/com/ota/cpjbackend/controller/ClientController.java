package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Client;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final MessagePropertie messagePropertie;
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Client client) {
        clientService.save(client);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.client")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(clientService.list());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Client client = clientService.load(id);
            return ResponseEntity.ok(client);
        } catch (AppException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            clientService.remove(id);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.deleted.success", "model.client")));
        } catch (AppException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

}
