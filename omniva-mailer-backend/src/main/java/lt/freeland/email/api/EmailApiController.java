package lt.freeland.email.api;

import lt.freeland.email.model.MessageCreateRequest;
import lt.freeland.email.model.MessageCreateResponse;
import lt.freeland.email.model.MessageResponse;
import lt.freeland.email.model.MessageResponseList;
import lt.freeland.email.service.IMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 *
 * @author freeland
 */
@Controller
public class EmailApiController implements EmailApi {

    private final IMessagingService emailService;

    @Autowired
    public EmailApiController(@Qualifier("EmailService") IMessagingService emailService) {
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<MessageResponse> getMessage(Long messageId) {
        return ResponseEntity.ok(emailService.getMessage(messageId));
    }

    @Override
    public ResponseEntity<MessageResponseList> getAllMessage() {
        return ResponseEntity.ok(emailService.getAllMessage());
    }

    @Override
    public ResponseEntity<MessageCreateResponse> createMessage(MessageCreateRequest body) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(emailService.createEmailMessage(body));
    }

    @Override
    public ResponseEntity<Void> editMessage(Long messageId, MessageCreateRequest body) {
        emailService.editEmailMessage(messageId, body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteMessage(Long messageId) {
        emailService.deleteEmailMessage(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }    
}
