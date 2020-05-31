package com.dictionary.infrastructure.notification.email;

import com.dictionary.common.EmailAddress;
import com.dictionary.domain.notification.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class SendGridEmailService implements EmailService {

    private final EmailServiceConfiguration emailServiceConfiguration;


    @Override
    public void sendEmail(com.dictionary.domain.notification.email.Email email) {
        Email from = new Email(emailServiceConfiguration.getFrom());

        Mail mail = new Mail();
        mail.setFrom(from);

        for (EmailAddress emailAddress : email.getTo()) {
            Personalization per = new Personalization();
            per.addTo(new Email(emailAddress.getValue()));
            mail.addPersonalization(per);
        }
        mail.setTemplateId("d-0688d369d6024464aeddb69c6ee7c013");
        send(mail);
    }

    private void send(Mail mail) throws EmailDeliveryFailedException {
        SendGrid sendGrid = new SendGrid(emailServiceConfiguration.getApiKey());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new EmailDeliveryFailedException();
        }
    }
}
