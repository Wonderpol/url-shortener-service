package com.piaskowy.urlshortenerbackend.email;

import com.piaskowy.urlshortenerbackend.config.FreeMarkerConfiguration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailService {

    private static final String NOREPLY_ADDRESS = "noreply@gmail.com";
    private final FreeMarkerConfiguration freeMarkerConfiguration;

    private final JavaMailSender javaMailSender;

    public EmailService(final FreeMarkerConfiguration freeMarkerConfiguration, final JavaMailSender javaMailSender) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
        this.javaMailSender = javaMailSender;
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    public void sendEmail(String to, String subject, Map<String, Object> templateModel) throws IOException, TemplateException, MessagingException {
        Configuration configuration = freeMarkerConfiguration.freemarkerClassLoaderConfig().getConfiguration();
        Template template = configuration.getTemplate("confirm-email.ftl");

        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);
        sendHtmlMessage(to, subject, htmlBody);
    }

}
