package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.MessagePropertie;
import br.com.ota.cpjbackend.model.User;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import br.com.ota.cpjbackend.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessagePropertie messagePropertie;
    private final PasswordEncoder encoder;

    public User updatePassword(UserRequest userRequest) throws NotFoundException {
        User user = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail())
                .orElseThrow(() -> new NotFoundException(messagePropertie.getMessage("message.not.found", "model.user", userRequest.getUsername())));

        user.setPassword(encoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return user;
    }
}
