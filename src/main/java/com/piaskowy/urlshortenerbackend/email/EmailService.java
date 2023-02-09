package com.piaskowy.urlshortenerbackend.email;

import com.piaskowy.urlshortenerbackend.email.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public EmailService(final JavaMailSender mailSender, final SpringTemplateEngine springTemplateEngine) {
        this.mailSender = mailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    public void sendHtmlEmail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.properties());
        helper.setFrom(email.from());
        helper.setTo(email.to());
        helper.setSubject(email.subject());
        String html = springTemplateEngine.process(email.template(), context);
        helper.setText(html, true);

        log.info("Sending email to: " + email.to());

        mailSender.send(message);
    }

}
