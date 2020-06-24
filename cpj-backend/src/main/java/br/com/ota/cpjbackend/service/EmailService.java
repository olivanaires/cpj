package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.model.vo.UserRequest;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class.getName());

    @Value("${app.sendgrip.api.key}")
    private String SENDGRID_API_KEY;

    public void sendNewUserEmail(UserRequest user) {

        Email from = new Email("airesolivan@gmail.com");
        String subject = "Usuário e Senha do NAP-CPJ";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", "Seus dados de acesso do sistema são " +
                "Usuário: " + user.getUsername() +
                " e Senha: " + user.getPassword()
                + user.getRole().toString().replaceFirst("ROLE_", " com Função: ") );

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        LOGGER.info("SENDGRID_API_KEY: " + SENDGRID_API_KEY);
        LOGGER.info("SENDGRID_API_KEY: " + SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
