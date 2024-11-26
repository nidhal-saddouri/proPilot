package com.propilot.performance_management_app.email;

import com.propilot.performance_management_app.model.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendRegistrationEmail(Users user) throws MessagingException {
        String subject = "Inscription réussie";

        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("lastName", user.getLastName());

        String htmlContent = templateEngine.process("registration-email", context);

        sendHtmlEmail(user.getEmail(), subject, htmlContent);
    }

    public void sendValidationEmail(Users user) throws MessagingException {
        String subject = "Validation de votre compte";

        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());

        String htmlContent = templateEngine.process("validation-email", context);

        sendHtmlEmail(user.getEmail(), subject, htmlContent);
    }


    public void sendPasswordResetEmail(Users user, String resetToken) throws MessagingException {
        String subject = "Réinitialisation de votre mot de passe";

        Context context = new Context();
        context.setVariable("firstName", user.getFirstName());

        String resetPasswordUrl = "http://localhost:4200/reset-password?token=" + resetToken;
        context.setVariable("resetPasswordUrl", resetPasswordUrl);

        String htmlContent = templateEngine.process("password-reset-email", context);

        sendHtmlEmail(user.getEmail(), subject, htmlContent);
    }



    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
    }

}
