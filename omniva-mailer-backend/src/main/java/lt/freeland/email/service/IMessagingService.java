package lt.freeland.email.service;

import lt.freeland.email.model.MessageCreateRequest;
import lt.freeland.email.model.MessageCreateResponse;
import lt.freeland.email.model.MessageResponse;
import lt.freeland.email.model.MessageResponseList;

/**
 *
 * @author freeland
 */
public interface IMessagingService {
        
    MessageResponse getMessage(Long messageId);

    MessageResponseList getAllMessage();

    MessageCreateResponse createEmailMessage(MessageCreateRequest body);

    void editEmailMessage(Long messageId, MessageCreateRequest body);

    void deleteEmailMessage(Long messageId);
}
