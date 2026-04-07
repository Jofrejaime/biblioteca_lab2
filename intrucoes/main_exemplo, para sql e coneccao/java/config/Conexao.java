package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
   private static Conexao instance;
   private Connection connection;
   private Properties properties;

   private Conexao()
   {
        this.properties = loadProperties();
   }

   public static synchronized Conexao getInstance()
   {
        if(instance == null)
        {
            instance = new Conexao();
        }
        return instance;
   }

   private Properties loadProperties()
   {
        Properties props = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties"))
        {
            if(input == null)
            {
               throw new RuntimeException("Não foi possível encontrar o arquivo de configuração do banco de dados.");
            }
            props.load(input);
        }catch (Exception e)
        {
            throw new RuntimeException("Erro ao carregar as propriedades do banco de dados: " + e.getMessage(), e);
        }
        return props;
    }
    public Connection getConnection()
    {
        try
        {
            if(connection == null || connection.isClosed())
            {
                connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
                );
            }
            return connection;

        }catch (SQLException e)
        {
            throw new RuntimeException("Erro ao connectar ao BD", e);
        }
    }
    public void closeConnection()
    {
        if(connection != null)
        {
            try
            {
                connection.close();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
