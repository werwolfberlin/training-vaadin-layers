package industries.werwolf.training.layers.persistence.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "industries.werwolf.training.layers.persistence.jpa")
@EntityScan(basePackages = "industries.werwolf.training.layers.persistence.jpa")
public class JpaConfiguration {
}
