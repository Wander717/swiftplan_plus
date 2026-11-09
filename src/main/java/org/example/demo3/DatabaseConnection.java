package org.example.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String URL = ConfigDB.getUrl()+ConfigDB.getName();
    private static final String USER = ConfigDB.getUsername();
    private static final String PASSWORD = ConfigDB.getPassword();

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Monta os dados dinamicamente no momento da conexão
                String url = ConfigDB.getUrl() + ConfigDB.getName();
                String user = ConfigDB.getUsername();
                String password = ConfigDB.getPassword();

                // Log preventivo para você verificar se os dados deixaram de ser null
                log.info("Tentando conectar em: " + url + " com o usuário: " + user);

                connection = DriverManager.getConnection(url, user, password);
                log.info("Conexão estabelecida com sucesso!");
            }
        } catch (ClassNotFoundException e) {
            log.error("Driver MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            log.error("Erro ao conectar ao banco: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("Conexão fechada.");
            }
        } catch (SQLException e) {
            log.error("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}