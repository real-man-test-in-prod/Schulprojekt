package com.rtfm.hammer; // Ensure this matches your project structure

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .outOfOrder(false)
                .load();

        System.out.println("--- MANUAL FLYWAY RUN STARTING ---");
        flyway.repair();
        flyway.migrate();
        System.out.println("--- MANUAL FLYWAY RUN FINISHED ---");

        return flyway;
    }
}