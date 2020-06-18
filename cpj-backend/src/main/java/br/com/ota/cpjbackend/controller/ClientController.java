package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.model.Client;
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

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Client client) {

        return ResponseEntity.ok(client);
    }
}
