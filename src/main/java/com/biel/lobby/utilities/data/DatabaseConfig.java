package com.biel.lobby.utilities.data;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/** PostgreSQL connection settings; secrets live in a server-local file. */
public record DatabaseConfig(String url, String user, String password) {
    public DatabaseConfig {
        if (url == null || !url.startsWith("jdbc:postgresql://") || url.toLowerCase(java.util.Locale.ROOT).contains("password=")) {
            throw new IllegalArgumentException("A PostgreSQL JDBC URL without embedded credentials is required");
        }
        if (user == null || user.isBlank() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Database user and password are required");
        }
    }

    public static DatabaseConfig load() {
        String configuredPath = System.getProperty("minicat.database.config",
                System.getenv().getOrDefault("MINICAT_DATABASE_CONFIG", "plugins/lobby/database.properties"));
        Properties settings = new Properties();
        try (Reader reader = Files.newBufferedReader(Path.of(configuredPath), StandardCharsets.UTF_8)) {
            settings.load(reader);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read Minicat database configuration: " + configuredPath, exception);
        }
        return new DatabaseConfig(settings.getProperty("url"), settings.getProperty("user"), settings.getProperty("password"));
    }

    Connection open(String applicationName) throws SQLException {
        Properties settings = new Properties();
        settings.setProperty("user", user);
        settings.setProperty("password", password);
        settings.setProperty("ApplicationName", applicationName);
        settings.setProperty("connectTimeout", "3");
        settings.setProperty("socketTimeout", "5");
        settings.setProperty("tcpKeepAlive", "true");
        settings.setProperty("options", "-c timezone=Europe/Madrid -c statement_timeout=4000 -c lock_timeout=2000");
        // Paper loads JDBC libraries in the plugin classloader, outside DriverManager's discovery.
        return new org.postgresql.Driver().connect(url, settings);
    }

    @Override public String toString() { return "DatabaseConfig[PostgreSQL, credentials redacted]"; }
}
