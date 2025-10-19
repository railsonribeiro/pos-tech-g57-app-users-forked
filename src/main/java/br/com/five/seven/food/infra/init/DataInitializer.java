package br.com.five.seven.food.infra.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        log.info("Initializing database");

        boolean tableExists = checkIfTableExists();

        if (!tableExists) {
            createTable();
        } else {
            log.info("Table tb_client already exists");
        }

        log.info("Database initialization completed");
    }

    private boolean checkIfTableExists() {
        String sql = "SELECT EXISTS (" +
                "SELECT FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_name = 'tb_client')";
        try {
            return jdbcTemplate.queryForObject(sql, Boolean.class);
        } catch (Exception e) {
            log.error("Error checking if table exists", e);
            return false;
        }
    }

    private void createTable() {
        String sql = """
                CREATE TABLE tb_client (
                    id VARCHAR(36) PRIMARY KEY,
                    cpf VARCHAR(14) NOT NULL,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    phone VARCHAR(20),
                    CONSTRAINT unique_cpf UNIQUE (cpf),
                    CONSTRAINT valid_email CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$')
                )
                """;
        try {
            jdbcTemplate.execute(sql);
            log.info("Table tb_client created successfully");
        } catch (Exception e) {
            log.error("Error creating table tb_client", e);
        }
    }
}
