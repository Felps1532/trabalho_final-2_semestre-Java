package com.example.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection conn = null;

    public static Connection getConnection() {
        String database = "floomes_database";
        String host = "localhost";
        String user = "root";
        String pass = "1234";
        String url = "jdbc:mysql://" + host + "/" + database;

        if (conn != null) {
            return conn;
        } else {
            try {
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Banco de dados conectado!");
                return conn;
            } catch (SQLException ex) {
                System.out.println("Erro ao conectar no banco de dados: " + database + "\n" + ex.getMessage());
                return null;
            }
        }
    }
}
