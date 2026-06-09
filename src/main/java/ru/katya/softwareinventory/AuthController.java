package ru.katya.softwareinventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.katya.softwareinventory.repository.DatabaseManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Контроллер окна аутентификации.
 * Обеспечивает вход в СУБД и переход к главному окну с учетом локализации.
 */
public class AuthController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    /**
     * Обработка нажатия кнопки входа.
     */
    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (usernameField.getText().trim().isEmpty() || passwordField.getText().isEmpty()) {
            errorLabel.setText("Введите логин и пароль!");
            return;
        }
        // 1. Пытаемся авторизоваться в PostgreSQL (п. 3.3 ТЗ)
        if (DatabaseManager.login(user, pass)) {
            try {
                // 2. Получаем текущую локаль, которую мы установили в HelloApplication
                Locale currentLocale = Locale.getDefault();

                // 3. Загружаем файл ресурсов для главного окна
                ResourceBundle bundle = ResourceBundle.getBundle("ru.katya.softwareinventory.app", currentLocale);

                // 4. Загружаем главное окно, ОБЯЗАТЕЛЬНО передавая bundle
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"), bundle);
                Scene scene = new Scene(loader.load());

                // 5. Переключаем окна
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setTitle(bundle.getString("app.title")); // Заголовок на нужном языке
                stage.setScene(scene);
                stage.setResizable(true); // Главное окно можно растягивать
                stage.centerOnScreen();

                AppLogger.info("Переход в главное меню выполнен. Локаль: " + currentLocale);

            } catch (IOException e) {
                AppLogger.info("Ошибка при загрузке главного окна: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Если в БД не пустили (п. 3.3)
            errorLabel.setText("Invalid login or password / Неверный вход");
        }
    }
}