package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Honorary;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.repository.HonoraryRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/honorary")
@RequiredArgsConstructor
public class HonoraryController {

    private final HonoraryRepository honoraryRepository;
    private final MessagePropertie messagePropertie;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Honorary honorary) {
        honoraryRepository.save(honorary);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.honorary")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Honorary> allHonorarys = honoraryRepository.findAll();
        return ResponseEntity.ok(allHonorarys);
    }

    @GetMapping("/list-this-month")
    public ResponseEntity<?> listThisMonth() {
        List<Honorary> allHonorarys = honoraryRepository.findAllByDateMonth();
        return ResponseEntity.ok(allHonorarys);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable Long id) {
        try {
            Honorary honorary = honoraryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.honorary")));
            return ResponseEntity.ok(honorary);
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/listByContract/{contractId}")
    public ResponseEntity<?> listByContract(@PathVariable Long contractId) {
        List<Honorary> allByCoAndContractId = honoraryRepository.findAllByContractId(contractId);
        return ResponseEntity.ok(allByCoAndContractId);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<MessageResponse> remove(@PathVariable Long id) {
        honoraryRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.removed.success", "model.honorary")));
    }

}
