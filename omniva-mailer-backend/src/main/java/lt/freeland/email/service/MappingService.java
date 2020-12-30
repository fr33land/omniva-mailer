package lt.freeland.email.service;

import java.util.List;
import static java.util.stream.Collectors.toCollection;
import lt.freeland.email.api.EmailApiController;
import lt.freeland.email.model.Link;
import lt.freeland.email.model.MessageCreateRequest;
import lt.freeland.email.model.MessageCreateResponse;
import lt.freeland.email.model.MessageCreateResponseLinks;
import lt.freeland.email.model.MessageResponse;
import lt.freeland.email.model.MessageResponseList;
import lt.freeland.email.model.Messages;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author freeland
 */
@Component
public class MappingService {

    public MessageResponse mapMessageToMessageResponse(Messages message) {
        return new MessageResponse()
                .messageId(message.getId())
                .messageReceiver(message.getMessageReceiver())
                .messageSubject(message.getMessageSubject())
                .message(message.getMessage())
                .delivered(message.getDelivered());
    }

    public MessageResponseList mapMessagesLisToMessageResponseList(List<Messages> list) {
        return list
                .stream()
                .map(m -> mapMessageToMessageResponse(m))
                .collect(toCollection(MessageResponseList::new));
    }

    public Messages mapMessageCreateRequestToMessages(MessageCreateRequest request) {
        return Messages
                .builder()
                .message(request.getMessage())
                .messageReceiver(request.getMessageReceiver())
                .messageSubject(request.getMessageSubject())
                .delivered(Boolean.FALSE)
                .build();
    }

    MessageCreateResponse mapMessageToMessageCreateResponse(Messages message) {
        return new MessageCreateResponse()
                .messageId(message.getId())
                .links(new MessageCreateResponseLinks()
                        .self(new Link()
                                .href(WebMvcLinkBuilder.linkTo(
                                        WebMvcLinkBuilder.methodOn(EmailApiController.class)._getMessage(message.getId())).withSelfRel().toUri().toString())
                        )
                );

    }

}
