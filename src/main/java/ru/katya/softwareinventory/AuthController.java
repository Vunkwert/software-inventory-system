package ru.katya.softwareinventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.katya.softwareinventory.repository.DatabaseManager;

import java.io.IOException;

/**
 * Контроллер окна аутентификации.
 * Реализует требования п. 3.3 ТЗ.
 */
public class AuthController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    protected void onLoginButtonClick() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (DatabaseManager.login(user, pass)) {
            try {
                // Загружаем главное окно
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                Scene scene = new Scene(loader.load());

                // Получаем текущую стадию (окно входа) и меняем её на главную
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setTitle("Система учета ПО - Главное меню");
                stage.setScene(scene);
                stage.setResizable(true); // Главное окно можно растягивать
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}