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
        System.out.println("=== Запуск системы учета ПО ===");

        // 1. ТЕСТ ПОДКЛЮЧЕНИЯ (Аутентификация через СУБД)
        // ВАЖНО: замени "1234" на свой пароль от PostgreSQL!
        String dbUser = "postgres";
        String dbPass = "1234";

        if (DatabaseManager.login(dbUser, dbPass)) {
            System.out.println("УСПЕХ: Вход в СУБД выполнен под пользователем: " + dbUser);

            // 2. ТЕСТ ВЫЗОВА ХРАНИМОЙ ПРОЦЕДУРЫ
            // Попробуем вызвать процедуру установки (например, для комнаты 1 и ПО 1)
            try {
                System.out.println("Вызов хранимой процедуры add_software_to_room...");
                SoftwareRepository repo = new SoftwareRepository();

                // ВНИМАНИЕ: Если в базе нет комнаты с ID 1, процедура просто ничего не изменит.
                repo.installSoftwareToRoom(1, 1);

                System.out.println("Тест процедуры завершен успешно.");
            } catch (Exception e) {
                System.err.println("Ошибка при вызове процедуры: " + e.getMessage());
            }
        } else {
            System.err.println("ОШИБКА: Не удалось подключиться к БД. Проверьте пароль и запущен ли сервер PostgreSQL.");
        }

        System.out.println("=== Запуск графического интерфейса JavaFX ===");
        // 3. ЗАПУСК ИНТЕРФЕЙСА
        Application.launch(HelloApplication.class, args);
    }
}