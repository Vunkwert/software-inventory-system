package ru.katya.softwareinventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.katya.softwareinventory.model.AuditLog;
import ru.katya.softwareinventory.model.Computer;
import ru.katya.softwareinventory.model.Software;
import ru.katya.softwareinventory.repository.ComputerRepository;
import ru.katya.softwareinventory.repository.ReportRepository;
import ru.katya.softwareinventory.repository.SoftwareRepository;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Главный контроллер приложения.
 * Управляет всеми вкладками: Компьютеры, ПО и Аудит.
 */
public class MainController {

    // --- Таблица Компьютеров ---
    @FXML private TableView<Computer> computerTable;
    @FXML private TableColumn<Computer, Long> idCol;
    @FXML private TableColumn<Computer, String> invCol;
    @FXML private TableColumn<Computer, String> ipCol;
    @FXML private TableColumn<Computer, String> cpuCol;
    @FXML private TableColumn<Computer, Integer> ramCol;

    // --- Таблица Реестра ПО ---
    @FXML private TableView<Software> softwareTable;
    @FXML private TableColumn<Software, Long> softIdCol;
    @FXML private TableColumn<Software, String> softNameCol;
    @FXML private TableColumn<Software, String> softVersionCol;
    @FXML private TableColumn<Software, String> softVendorCol;

    private final ComputerRepository computerRepository = new ComputerRepository();
    private final SoftwareRepository softwareRepository = new SoftwareRepository();
    private final ReportRepository reportRepository = new ReportRepository();

    /**
     * Вызывается автоматически при запуске окна.
     * Связывает колонки таблиц с полями классов-моделей.
     */
    @FXML
    public void initialize() {
        // 1. Настройка колонок Компьютеров
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuInfo"));
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ramGb"));

        // 2. Настройка колонок Реестра ПО
        softIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        softNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        softVersionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        softVendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));

        // Загружаем данные во все таблицы при старте
        onRefreshComputers();
        onRefreshSoftware();
    }

    @FXML
    public void onRefreshComputers() {
        computerTable.setItems(computerRepository.getAllComputers());
    }

    @FXML
    public void onRefreshSoftware() {
        softwareTable.setItems(softwareRepository.getAllSoftware());
    }


    /**
     * Открывает модальное окно для установки ПО (вызов хранимой процедуры).
     */
    @FXML
    public void onAddSoftwareClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("install-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Установка ПО - Вызов процедуры");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // Ждем закрытия окна установки

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitClick() {
        Platform.exit();
    }
}