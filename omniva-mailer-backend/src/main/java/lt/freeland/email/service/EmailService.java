package lt.freeland.email.service;

import lombok.extern.slf4j.Slf4j;
import lt.freeland.email.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 *
 * @author r.sabaliauskas
 */
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.sender}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(MimeMessagePreparator email) {
        //mailSender.send(email);
    }

    public void prepareAndSend(Messages email) {
        MimeMessagePreparator messagePreparator = prepareEmailMessage(email);
        sendEmail(messagePreparator);
    }

    private MimeMessagePreparator prepareEmailMessage(Messages email) {
        return (mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(sender);
            messageHelper.setTo(email.getMessageReceiver());
            messageHelper.setSubject(email.getMessageSubject());
            messageHelper.setText(email.getMessage(), false);
        };
    }
}
