package ru.katya.softwareinventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс запуска приложения с поддержкой локализации (п. 8 методички).
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // 1. Устанавливаем язык для ВСЕГО приложения (меняй на "en" или "de" для теста)
            Locale currentLocale = new Locale("en");
            Locale.setDefault(currentLocale);

            // 2. Загружаем файл ресурсов (Resource Bundle)
            // Путь: пакет + базовое имя файла (app)
            ResourceBundle bundle = ResourceBundle.getBundle("ru.katya.softwareinventory.app", currentLocale);

            // 3. Загружаем FXML и передаем ему bundle для перевода интерфейса
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("auth-view.fxml"), bundle);

            Scene scene = new Scene(fxmlLoader.load());

            // Берем заголовок окна из файла локализации
            stage.setTitle(bundle.getString("app.title"));
            stage.setScene(scene);
            stage.setResizable(false); // Окно входа фиксированного размера
            stage.show();

            AppLogger.info("Приложение успешно запущено. Локаль: " + currentLocale);

        } catch (Exception e) {
            System.err.println("Ошибка при запуске приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}