package ru.katya.softwareinventory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.katya.softwareinventory.repository.SoftwareRepository;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Контроллер для окна запуска хранимой процедуры установки ПО.
 * Реализует защиту от некорректного ввода и локализацию ошибок.
 */
public class InstallController {
    @FXML private TextField softwareIdField;
    @FXML private TextField roomIdField;

    private final SoftwareRepository repository = new SoftwareRepository();

    @FXML
    private void onStartInstall() {
        String roomNumber = roomIdField.getText().trim();
        String softText = softwareIdField.getText().trim();

        // 1. Проверка на пустоту
        if (roomNumber.isEmpty() || softText.isEmpty()) {
            showError("%error.empty");
            return;
        }

        try {
            int softId = Integer.parseInt(softText);

            // 2. Вызов хранимой процедуры (передаем НОМЕР аудитории и ID софта)
            repository.installSoftwareToRoom(roomNumber, softId);

            // 3. Успех
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ResourceBundle bundle = ResourceBundle.getBundle("ru.katya.softwareinventory.app", Locale.getDefault());
            alert.setTitle(bundle.getString("app.title"));
            alert.setHeaderText(null);
            alert.setContentText("Success / Успешно!");
            alert.showAndWait();

            onClose();

        } catch (NumberFormatException e) {
            showError("%error.not_number");
        } catch (Exception e) {
            // 4. Ловим специфические ошибки из базы данных (RAISE EXCEPTION)
            String dbError = e.getMessage();
            if (dbError != null && dbError.contains("ROOM_NOT_FOUND")) {
                showError("%error.room_not_found");
            } else if (dbError != null && dbError.contains("NO_COMPUTERS_IN_ROOM")) {
                showError("%error.no_pcs");
            } else {
                showError(dbError);
            }
        }
    }

    /**
     * Универсальный метод вывода ошибок с поддержкой локализации.
     */
    private void showError(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("ru.katya.softwareinventory.app", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(bundle.getString("error.title"));
        alert.setHeaderText(null);

        // Если ключ начинается с %, берем перевод, иначе выводим текст как есть
        String message = key.startsWith("%") ? bundle.getString(key.substring(1)) : key;
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onClose() {
        ((Stage) roomIdField.getScene().getWindow()).close();
    }
}