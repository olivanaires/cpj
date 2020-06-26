package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Lawyer;
import br.com.ota.cpjbackend.model.enums.RoleName;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.LawyerRepository;
import br.com.ota.cpjbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lawyer")
@RequiredArgsConstructor
public class LawyerController {

    private final MessagePropertie messagePropertie;
    private final LawyerRepository lawyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody Lawyer lawyerRequest) {
        if (lawyerRepository.existsByOabNumber(lawyerRequest.getOabNumber())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("lawyer.exist", lawyerRequest.getOabNumber())));
        }

        String password = new RandomString(8).nextString();
        lawyerRequest.getUser().setPassword(passwordEncoder.encode(password));
        lawyerRepository.save(lawyerRequest);

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(lawyerRequest.getUser().getEmail());
        userRequest.setPassword(password);
        userRequest.setUsername(lawyerRequest.getUser().getUsername());
        userRequest.setRole(RoleName.ROLE_LAWYER);
        emailService.sendNewUserEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.lawyer")));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Lawyer> lawyers = lawyerRepository.findAll(Sort.by("name"));
        return ResponseEntity.ok(lawyers);
    }

}
