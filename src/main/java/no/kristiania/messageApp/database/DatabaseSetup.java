package no.kristiania.messageApp.database;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DatabaseSetup {

    public static HikariDataSource getDataSource() throws IOException {

        var properties = new Properties();
        try (var reader = new FileReader("application.properties")) {
            properties.load(reader);
        }
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getProperty("dataSource.url"));
        dataSource.setUsername(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setConnectionTimeout(2000);
        Flyway.configure().dataSource(dataSource).load().migrate();

        return dataSource;
    }
}
