package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.exception.AppException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

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
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AppException(messagePropertie.getMessage("user.exist", userRequest.getUsername())));
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AppException(messagePropertie.getMessage("user.exist", userRequest.getEmail())));
        }

        User user = new User(userRequest.getUsername(),
                userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()));

        Role role = roleRepository.findByName(userRequest.getRole());

        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        emailService.sendNewUserEmail(userRequest);

        return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.created.success", "model.user")));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        try {
            userService.updatePassword(userRequest);
            return ResponseEntity.ok(new MessageResponse(messagePropertie.getMessage("message.updated.success", "model.user.password")));
        } catch (NotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(new AppException(messagePropertie.getMessage("message.not.found", "model.user", userRequest.getUsername())));
        }
    }

    @GetMapping("/passwordRefresh/{email}")
    public ResponseEntity<?> passwordRefresh(@PathVariable String email) {
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
                    .body(new AppException(messagePropertie.getMessage("message.not.found", "model.user.email", email)));
        }
    }

}
