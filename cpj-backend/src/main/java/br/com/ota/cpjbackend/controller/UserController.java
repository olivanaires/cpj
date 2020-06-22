package br.com.ota.cpjbackend.controller;

import br.com.ota.cpjbackend.exception.AppException;
import br.com.ota.cpjbackend.model.Role;
import br.com.ota.cpjbackend.model.User;
import br.com.ota.cpjbackend.model.vo.MessageResponse;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.RoleRepository;
import br.com.ota.cpjbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AppException("Usuário já cadastrado."));
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AppException("E-Mail já esta sendo utilizado por outro Usuário.'"));
        }

        User user = new User(userRequest.getUsername(),
                userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()));

        Role role = roleRepository.findByName(userRequest.getRole());

        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuário cadastrado com sucesso."));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        User user = userRepository.findByUsernameOrEmail(userRequest.getUsername(), null).orElse(null);

        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest()
                    .body(new AppException("Usuário " + userRequest.getUsername() + " não encontrado."));
        }

        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Senha alterada com sucesso."));
    }

}
