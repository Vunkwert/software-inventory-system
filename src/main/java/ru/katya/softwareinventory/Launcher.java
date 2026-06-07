package ru.katya.softwareinventory;

import javafx.application.Application;
import ru.katya.softwareinventory.repository.DatabaseManager;
import ru.katya.softwareinventory.repository.SoftwareRepository;

/**
 * Главный класс для запуска приложения.
 * Содержит тестовую логику подключения к БД (согласно п. 3.2 и 3.3 ТЗ).
 */
public class Launcher {

    public static void main(String[] args) {
        // Теперь мы не пытаемся войти здесь с жестким паролем.
        // Мы просто запускаем окно интерфейса.
        Application.launch(HelloApplication.class, args);
    }
}