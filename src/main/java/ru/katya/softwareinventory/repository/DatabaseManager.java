package ru.katya.softwareinventory.repository;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/software_inventory";
    private static Connection connection;
    private static Properties queries = new Properties();

    static {
        // Загружаем SQL запросы при старте программы
        try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("queries.properties")) {
            if (input != null) queries.load(input);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static String getQuery(String key) {
        return queries.getProperty(key);
    }

    public static boolean login(String username, String password) {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            ru.katya.softwareinventory.AppLogger.info("Пользователь " + username + " успешно вошел в систему.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
    public void loadQueries() {
        try {
            Properties properties = new Properties();
            URL url = getClass().getResource("/queries.properties");
            properties.load(new FileInputStream(url.getFile()));
            // Теперь можно брать запросы: properties.getProperty("sql.find_all")
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}