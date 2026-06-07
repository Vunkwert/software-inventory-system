package ru.katya.softwareinventory.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для управления подключением к СУБД PostgreSQL.
 * Реализует аутентификацию через механизмы СУБД (п. 3.3 ТЗ).
 */
public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/software_inventory";
    private static Connection connection;

    /**
     * Пытается установить соединение с БД под конкретным пользователем.
     */
    public static boolean login(String username, String password) {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка аутентификации в СУБД: " + e.getMessage());
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}