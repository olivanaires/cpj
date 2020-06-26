package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.Role;
import br.com.ota.cpjbackend.model.User;
import br.com.ota.cpjbackend.model.enums.RoleName;
import br.com.ota.cpjbackend.model.vo.LawyerRequest;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.LawyerRepository;
import br.com.ota.cpjbackend.repository.RoleRepository;
import br.com.ota.cpjbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

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
        if (lawyerRepository.existsByOabNumber(lawyerRequest.getOabNumber())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("lawyer.exist", lawyerRequest.getOabNumber())));
        }

        UserRequest userRequest = lawyerRequest.getUser();
        User user = new User(userRequest.getUsername(), userRequest.getEmail(), encoder.encode(userRequest.getPassword()));
        Role role = roleRepository.findByName(userRequest.getRole());
        user.setRoles(Collections.singleton(role));

        Lawyer lawyer = new Lawyer();
        lawyer.setName(lawyerRequest.getName());
        lawyer.setOabNumber(lawyerRequest.getOabNumber());
        lawyer.setUser(user);
        lawyerRepository.save(lawyer);

        emailService.sendNewUserEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.lawyer")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Lawyer> lawyers = lawyerRepository.findAll(Sort.by("name"));
        return ResponseEntity.ok(lawyers);
    }

}
