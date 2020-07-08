package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.Role;
import br.com.ota.cpjbackend.model.User;
import br.com.ota.cpjbackend.model.vo.LawyerRequest;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.LawyerRepository;
import br.com.ota.cpjbackend.repository.RoleRepository;
import br.com.ota.cpjbackend.service.EmailService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/lawyer")
@RequiredArgsConstructor
public class LawyerController {

    private final MessagePropertie messagePropertie;
    private final LawyerRepository lawyerRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody LawyerRequest lawyerRequest) {

        Lawyer lawyer;
        User user;
        UserRequest userRequest = lawyerRequest.getUser();
        if (Objects.nonNull(lawyerRequest.getId())) {
            try {
                lawyer = lawyerRepository.findById(lawyerRequest.getId())
                        .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.lawyer")));
                lawyer.getUser().setUsername(userRequest.getUsername());
                lawyer.getUser().setEmail(userRequest.getEmail());
                lawyer.getUser().setPassword(encoder.encode(userRequest.getPassword()));

                lawyer.setName(lawyerRequest.getName());
                lawyer.setOabNumber(lawyerRequest.getOabNumber());
            } catch (NotFoundException ex) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(ex.getMessage()));
            }
        } else {
            if (lawyerRepository.existsByOabNumber(lawyerRequest.getOabNumber())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(messagePropertie.getMessage("lawyer.exist", lawyerRequest.getOabNumber())));
            }

            user = new User(userRequest.getUsername(), userRequest.getEmail(), encoder.encode(userRequest.getPassword()));
            Role role = roleRepository.findByName(userRequest.getRole());
            user.setRoles(Collections.singleton(role));

            lawyer = new Lawyer();
            lawyer.setName(lawyerRequest.getName());
            lawyer.setOabNumber(lawyerRequest.getOabNumber());
            lawyer.setUser(user);
        }

        lawyerRepository.save(lawyer);
        emailService.sendNewUserEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.lawyer")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Lawyer> lawyers = lawyerRepository.findAll(Sort.by("name"));
        return ResponseEntity.ok(lawyers);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            Lawyer lawyer = lawyerRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.lawyer")));
            return ResponseEntity.ok(lawyer.toLawyerRequest());
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

}
