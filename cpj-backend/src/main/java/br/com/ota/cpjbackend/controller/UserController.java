package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.Role;
import br.com.ota.cpjbackend.model.User;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.RoleRepository;
import br.com.ota.cpjbackend.repository.UserRepository;
import br.com.ota.cpjbackend.service.EmailService;
import br.com.ota.cpjbackend.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final MessagePropertie messagePropertie;
    private final EmailService emailService;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody UserRequest userRequest) {

        User user;
        if (Objects.nonNull(userRequest.getId())) {
            try {
                user = userRepository.findById(userRequest.getId())
                        .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.user")));
                user.setUsername(userRequest.getUsername());
                user.setEmail(userRequest.getEmail());
                user.setPassword(encoder.encode(userRequest.getPassword()));
            } catch (NotFoundException ex) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(messagePropertie.getMessage("message.model.not.found", "model.user")));
            }
        } else {
            if (userRepository.existsByUsername(userRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(messagePropertie.getMessage("user.exist", userRequest.getUsername())));
            }

            if (userRepository.existsByEmail(userRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(messagePropertie.getMessage("user.exist", userRequest.getEmail())));
            }
            user  = new User(userRequest.getUsername(), userRequest.getEmail(), encoder.encode(userRequest.getPassword()));
            Role role = roleRepository.findByName(userRequest.getRole());
            user.setRoles(Collections.singleton(role));
        }

        userRepository.save(user);

        emailService.sendNewUserEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.user")));
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestBody UserRequest userRequest) {
        try {
            userService.updatePassword(userRequest);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.updated.success", "model.user.password")));
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.not.found", "model.user", userRequest.getUsername())));
        }
    }

    @GetMapping("/passwordRefresh/{email}")
    public ResponseEntity<MessageResponse> passwordRefresh(@PathVariable String email) {
        try {
            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(email);
            userRequest.setPassword(new RandomString(8).nextString());
            User user = userService.updatePassword(userRequest);

            userRequest.setUsername(user.getUsername());
            userRequest.setRole(user.getRoles().iterator().next().getName());
            emailService.sendNewUserEmail(userRequest);

            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.resent.email.password", userRequest.getEmail())));
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(messagePropertie.getMessage("message.not.found", "model.user.email", email)));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<User> users = userRepository.findAll(Sort.by("username"));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<?> load(@PathVariable String id) {
        try {
            User user = userRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.model.not.found", "model.user")));
            return ResponseEntity.ok(user.toUserRequest());
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(ex.getMessage()));
        }
    }

}
