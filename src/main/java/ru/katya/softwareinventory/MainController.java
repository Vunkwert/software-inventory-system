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
import ru.katya.softwareinventory.repository.ComputerRepository;
import ru.katya.softwareinventory.repository.ReportRepository;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Главный контроллер приложения.
 * Управляет таблицами компьютеров и системного аудита.
 */
public class MainController {

    // --- Поля для таблицы Компьютеров ---
    @FXML private TableView<Computer> computerTable;
    @FXML private TableColumn<Computer, Long> idCol;
    @FXML private TableColumn<Computer, String> invCol;
    @FXML private TableColumn<Computer, String> ipCol;
    @FXML private TableColumn<Computer, String> cpuCol;
    @FXML private TableColumn<Computer, Integer> ramCol;

    // --- Поля для таблицы Аудита (п. 3.4 ТЗ) ---
    @FXML private TableView<AuditLog> auditTable;
    @FXML private TableColumn<AuditLog, Long> auditIdCol;
    @FXML private TableColumn<AuditLog, String> auditUserCol;
    @FXML private TableColumn<AuditLog, String> auditOpCol;
    @FXML private TableColumn<AuditLog, String> auditTableCol;
    @FXML private TableColumn<AuditLog, LocalDateTime> auditTimeCol;

    private final ComputerRepository computerRepository = new ComputerRepository();
    private final ReportRepository reportRepository = new ReportRepository();

    /**
     * Метод инициализации. Вызывается автоматически при загрузке FXML.
     */
    @FXML
    public void initialize() {
        // 1. Настройка колонок для таблицы Компьютеров
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuInfo"));
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ramGb"));

        // 2. Настройка колонок для таблицы Аудита
        auditIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        auditUserCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        auditOpCol.setCellValueFactory(new PropertyValueFactory<>("operation"));
        auditTableCol.setCellValueFactory(new PropertyValueFactory<>("tableName"));
        auditTimeCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // 3. Первичная загрузка данных
        onRefreshComputers();
        onRefreshAudit();
    }

    @FXML
    public void onRefreshComputers() {
        computerTable.setItems(computerRepository.getAllComputers());
    }

    @FXML
    public void onRefreshAudit() {
        auditTable.setItems(reportRepository.getAuditLogs());
    }

    @FXML
    public void onAddSoftwareClick() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("install-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Установка ПО - Вызов процедуры");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // Ждем закрытия

            // После установки ПО обновляем аудит, чтобы увидеть новую запись
            onRefreshAudit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitClick() {
        Platform.exit();
    }
}