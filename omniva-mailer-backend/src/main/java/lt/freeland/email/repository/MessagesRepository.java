package lt.freeland.email.repository;

import java.util.List;
import lt.freeland.email.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author freeland
 */
@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    
    List<Messages> findByDelivered(Boolean delivered);
}
