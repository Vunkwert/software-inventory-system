package ru.katya.softwareinventory;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.katya.softwareinventory.repository.DatabaseManager;

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
            // Если вход успешен, пока просто выводим сообщение.
            // В следующем блоке мы заменим это на открытие главного окна.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Вы успешно вошли в СУБД под пользователем " + user);
            alert.showAndWait();
        } else {
            errorLabel.setText("Ошибка входа: неверный логин или пароль");
        }
    }
}