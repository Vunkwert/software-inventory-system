package ru.katya.softwareinventory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.katya.softwareinventory.repository.SoftwareRepository;

/**
 * Контроллер для окна запуска хранимой процедуры установки ПО.
 */
public class InstallController {
    @FXML private TextField softwareIdField;
    @FXML private TextField roomIdField;

    private final SoftwareRepository repository = new SoftwareRepository();

    @FXML
    private void onStartInstall() {
        try {
            int softId = Integer.parseInt(softwareIdField.getText());
            int roomId = Integer.parseInt(roomIdField.getText());

            // Вызов хранимой процедуры (п. 3.2 ТЗ)
            repository.installSoftwareToRoom(roomId, softId);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Процедура выполнена");
            alert.setContentText("ПО успешно привязано к компьютерам в базе данных.");
            alert.showAndWait();

            onClose(); // Закрываем окно после успеха
        } catch (NumberFormatException e) {
            showError("Ошибка ввода", "ID должны быть числами!");
        } catch (Exception e) {
            showError("Ошибка БД", e.getMessage());
        }
    }

    @FXML
    private void onClose() {
        ((Stage) roomIdField.getScene().getWindow()).close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}