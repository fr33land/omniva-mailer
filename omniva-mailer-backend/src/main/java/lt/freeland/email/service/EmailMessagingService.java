package lt.freeland.email.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lt.freeland.email.model.MessageCreateRequest;
import lt.freeland.email.model.MessageCreateResponse;
import lt.freeland.email.model.MessageResponse;
import lt.freeland.email.model.MessageResponseList;
import lt.freeland.email.model.Messages;
import lt.freeland.email.repository.MessagesRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author freeland
 */
@Service("EmailService")
@AllArgsConstructor
public class EmailMessagingService implements IMessagingService {

    private final MappingService mapper;
    private final MessagesRepository messagesRepository;

    @Override
    public MessageResponse getMessage(Long messageId) {
        Messages message = messagesRepository
                .findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Message %s not found", messageId)));   
        return mapper.mapMessageToMessageResponse(message);
    }

    @Override
    public MessageResponseList getAllMessage() {
        List<Messages> messages = messagesRepository.findAll();
        return mapper.mapMessagesLisToMessageResponseList(messages);
    }

    @Override
    public MessageCreateResponse createEmailMessage(MessageCreateRequest body) {
        Messages message = mapper.mapMessageCreateRequestToMessages(body);
        message = messagesRepository.save(message);
        return mapper.mapMessageToMessageCreateResponse(message);
    }

    @Override
    public void editEmailMessage(Long messageId, MessageCreateRequest body) {
        Messages message = mapper.mapMessageCreateRequestToMessages(body);
        message.setId(messageId);
        messagesRepository.save(message);
    }

    @Override
    public void deleteEmailMessage(Long messageId) {
        messagesRepository.deleteById(messageId);
    }    

}
