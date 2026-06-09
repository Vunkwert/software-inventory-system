package ru.katya.softwareinventory;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.katya.softwareinventory.model.Computer;
import ru.katya.softwareinventory.model.ReportItem;
import ru.katya.softwareinventory.model.Software;
import ru.katya.softwareinventory.repository.ComputerRepository;
import ru.katya.softwareinventory.repository.ReportRepository;
import ru.katya.softwareinventory.repository.SoftwareRepository;

import java.io.IOException;

/**
 * Главный контроллер приложения.
 * Реализует логику отображения данных и формирования отчетов.
 */
public class MainController {

    // --- Таблица Компьютеров ---
    @FXML private TableView<Computer> computerTable;
    @FXML private TableColumn<Computer, Long> idCol;
    @FXML private TableColumn<Computer, String> roomCol;
    @FXML private TableColumn<Computer, String> empCol;
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

    // --- Таблица Отчетов ---
    @FXML private TableView<ReportItem> reportTable;
    @FXML private TableColumn<ReportItem, String> repRoomCol;
    @FXML private TableColumn<ReportItem, String> repSoftCol;
    @FXML private TableColumn<ReportItem, String> repVerCol;
    @FXML private TableColumn<ReportItem, String> repPcCol;

    @FXML private ComboBox<String> reportTypeCombo;
    @FXML private TextField reportParamField;

    @FXML private TableView<Software> detailsTable;
    @FXML private TableColumn<Software, String> detNameCol;
    @FXML private TableColumn<Software, String> detVerCol;
    @FXML private TableColumn<Software, String> detVendorCol;

    // Репозитории
    private final ComputerRepository computerRepository = new ComputerRepository();
    private final SoftwareRepository softwareRepository = new SoftwareRepository();
    private final ReportRepository reportRepository = new ReportRepository();

    /**
     * Инициализация контроллера. Настройка связей таблиц с моделями.
     */
    @FXML
    public void initialize() {

        // 1. Настройка колонок Компьютеров
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        empCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuInfo"));
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ramGb"));

        // 2. Настройка колонок Реестра ПО
        softIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        softNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        softVersionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        softVendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));

        // 3. Настройка колонок Отчетов
        repRoomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        repSoftCol.setCellValueFactory(new PropertyValueFactory<>("software"));
        repVerCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        repPcCol.setCellValueFactory(new PropertyValueFactory<>("pcInventory"));

        // Настройка выпадающего списка отчетов
        reportTypeCombo.setItems(FXCollections.observableArrayList(
                "По аудитории", "По предназначению", "По категории"
        ));
        reportTypeCombo.getSelectionModel().selectFirst();

        // Первичная загрузка данных
        onRefreshComputers();
        onRefreshSoftware();

        AppLogger.info("Главное окно успешно инициализировано.");
        // 4. Настройка таблицы деталей ПО
        detNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        detVerCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        detVendorCol.setCellValueFactory(new PropertyValueFactory<>("vendor"));

// СЛУШАТЕЛЬ ВЫБОРА: когда кликаем на компьютер
        computerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Загружаем ПО только для выбранного ПК
                detailsTable.setItems(softwareRepository.getSoftwareForComputer(newSelection.getId()));
                AppLogger.info("Просмотр ПО для компьютера ID: " + newSelection.getId());
            }
        });
    }

    @FXML
    public void onRefreshComputers() {
        computerTable.setItems(computerRepository.getAllComputers());
        AppLogger.info("Список компьютеров обновлен.");
    }

    @FXML
    public void onRefreshSoftware() {
        softwareTable.setItems(softwareRepository.getAllSoftware());
        AppLogger.info("Реестр ПО обновлен.");
    }

    /**
     * Формирует отчет на основе выбранного типа и введенного параметра.
     * Реализует требования п. 3 ТЗ и защиту от некорректного ввода.
     */
    @FXML
    public void onGenerateReport() {
        // 1. Получаем индекс выбранного элемента (0, 1 или 2)
        // Это надежнее, чем сравнивать текст, который меняется при локализации
        int selectedIndex = reportTypeCombo.getSelectionModel().getSelectedIndex();

        // 2. Получаем параметр и очищаем от лишних пробелов
        String param = (reportParamField.getText() != null) ? reportParamField.getText().trim() : "";

        // 3. ВАЛИДАЦИЯ: Если поле пустое - подсвечиваем красным и выходим
        if (param.isEmpty()) {
            reportParamField.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 2px;");
            AppLogger.info("Предупреждение: Попытка сформировать отчет без указания параметра.");
            return;
        } else {
            // Если данные введены - убираем красную рамку
            reportParamField.setStyle("");
        }

        String queryKey = "";

        // Определение ключа SQL-запроса на основе индекса в ComboBox
        switch (selectedIndex) {
            case 0 -> queryKey = "sql.report.byRoom";     // По аудитории
            case 1 -> queryKey = "sql.report.byPurpose";  // По предназначению
            case 2 -> queryKey = "sql.report.byCategory"; // По категории
            default -> {
                AppLogger.info("Ошибка: Тип отчета не выбран.");
                return;
            }
        }

        // 4. Выполняем запрос через репозиторий и обновляем таблицу
        try {
            reportTable.setItems(reportRepository.generateSoftwareReport(queryKey, param));

            // Если данных не найдено, запишем это в лог для информации
            if (reportTable.getItems().isEmpty()) {
                AppLogger.info("Отчет сформирован, но данных по значению '" + param + "' не найдено.");
            } else {
                AppLogger.info("Успешно сформирован отчет по ключу: " + queryKey + " со значением: " + param);
            }
        } catch (Exception e) {
            AppLogger.info("Критическая ошибка при генерации отчета: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Открытие окна установки ПО
     */
    @FXML
    public void onAddSoftwareClick() {
        try {
            // 1. Получаем текущий бандл (словарь) на основе выбранного языка
            java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle(
                    "ru.katya.softwareinventory.app",
                    java.util.Locale.getDefault()
            );

            // 2. ПЕРЕДАЕМ bundle в FXMLLoader
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("install-view.fxml"), bundle);

            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("install.header"));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            // Показываем окно и ждем закрытия
            stage.showAndWait();

            // Мы удалили onRefreshAudit(), так как пишем логи в файл app.log автоматически
            AppLogger.info("Окно установки ПО было закрыто.");

        } catch (IOException e) {
            AppLogger.info("Ошибка при открытии окна установки: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void onAboutClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Система учета ПО");
        alert.setContentText("Разработчик: Свистунова Е.М.\nГруппа: ПИН-123\n2026г.");
        alert.showAndWait();
    }

    @FXML
    public void onExitClick() {
        AppLogger.info("Выход из приложения.");
        Platform.exit();
    }
}