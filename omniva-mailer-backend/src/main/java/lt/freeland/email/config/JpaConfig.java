package lt.freeland.email.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author r.sabaliauskas
 */
@Configuration
@EnableJpaRepositories(value = {"lt.freeland.email.repository"})
@EntityScan(value = {"lt.freeland.email.model"})
public class JpaConfig {
    
}
