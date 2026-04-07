package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static Conexao instance;
    private final Properties properties;

    private Conexao() {
        this.properties = loadProperties();
    }

    public static synchronized Conexao getInstance() {
        if (instance == null) {
            instance = new Conexao();
        }
        return instance;
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo database.properties nao encontrado na classpath.");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configuracao da base de dados.", e);
        }
        return props;
    }

    public Connection getConnection() {
        try {
            String driver = properties.getProperty("db.driver");
            if (driver != null && !driver.isBlank()) {
                Class.forName(driver);
            }

            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }
}