package br.com.ota.cpjbackend.configuration;

import br.com.ota.cpjbackend.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "br.com.ota.cpjbackend.repository",
        repositoryBaseClass = BaseRepositoryImpl.class)
public class CustomRepository {
}
