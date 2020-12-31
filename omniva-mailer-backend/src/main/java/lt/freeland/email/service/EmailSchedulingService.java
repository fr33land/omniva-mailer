package lt.freeland.email.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.freeland.email.model.Messages;
import lt.freeland.email.repository.MessagesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author freeland
 */
@Slf4j
@Component
@AllArgsConstructor
public class EmailSchedulingService {

    private final MessagesRepository messagesRepository;
    private final EmailService emailService;

    @Scheduled(cron = "* * */1 * * *")
    public void proceedEmailsQueue() {
        log.info("Started running email queue at {}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        List<Messages> messages = messagesRepository.findByDelivered(Boolean.FALSE);
        messages.stream().forEach(m -> {
            emailService.prepareAndSend(m);
            m.setDelivered(Boolean.TRUE);
            messagesRepository.save(m);
        });
        log.info("Finished running email queue at {} with {} queue size", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), messages.size());
    }

}
