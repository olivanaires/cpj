package br.com.ota.cpjbackend.service;

import br.com.ota.cpjbackend.configuration.util.DynamicTemplatePersonalization;
import br.com.ota.cpjbackend.model.vo.UserRequest;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${app.sendgrip.api.key}")
    private String SENDGRID_API_KEY;

    public void sendNewUserEmail(UserRequest userRequest) {

        String subject = "Usuário e Senha do NAP-CPJ";

        Email from = new Email("airesolivan@gmail.com");
        Email to = new Email("olivanaires@gmail.com");

        DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.addDynamicTemplateData("username", userRequest.getUsername());
        personalization.addDynamicTemplateData("password", userRequest.getPassword());
        personalization.addDynamicTemplateData("role", userRequest.getRole());
        personalization.addTo(to);

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.setTemplateId("d-f147337f72ca411dbdb3c660fa941ad6");
        mail.addPersonalization(personalization);

        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            SendGrid sg = new SendGrid(SENDGRID_API_KEY);
            sg.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
