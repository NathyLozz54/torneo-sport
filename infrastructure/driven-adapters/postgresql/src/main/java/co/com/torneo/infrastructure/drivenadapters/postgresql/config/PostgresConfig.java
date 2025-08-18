package co.com.torneo.infrastructure.drivenadapters.postgresql.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@Profile("postgres")
@EnableR2dbcRepositories(basePackages = "co.com.torneo.infrastructure.drivenadapters.postgresql.repository")
public class PostgresConfig {
}
