package lt.freeland.email.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author r.sabaliauskas
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGES")
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_gen")
    @SequenceGenerator(name="msg_gen", sequenceName="messages_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "MESSAGE_RECEIVER", length = 50)
    private String messageReceiver;

    @Column(name = "MESSAGE_SUBJECT", length = 128)
    private String messageSubject;

    @Column(name = "MESSAGE", length = 500)
    private String message;
    
    @Column(name = "DELIVERED")
    private Boolean delivered;
}
