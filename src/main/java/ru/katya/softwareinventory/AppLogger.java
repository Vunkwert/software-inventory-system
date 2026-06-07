package ru.katya.softwareinventory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * Класс для логирования действий в файл и консоль.
 */
public class AppLogger {
    private static final String LOG_FILE = "app.log";

    public static void info(String message) {
        String logEntry = LocalDateTime.now() + " [INFO]: " + message;
        System.out.println(logEntry); // В консоль

        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(logEntry); // В файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}