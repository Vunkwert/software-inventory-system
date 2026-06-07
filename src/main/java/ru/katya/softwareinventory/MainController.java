package ru.katya.softwareinventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.katya.softwareinventory.model.Computer;
import ru.katya.softwareinventory.repository.ComputerRepository;

public class MainController {
    @FXML private TableView<Computer> computerTable;
    @FXML private TableColumn<Computer, Long> idCol;
    @FXML private TableColumn<Computer, String> invCol;
    @FXML private TableColumn<Computer, String> ipCol;
    @FXML private TableColumn<Computer, String> cpuCol;
    @FXML private TableColumn<Computer, Integer> ramCol;

    private final ComputerRepository repository = new ComputerRepository();

    @FXML
    public void initialize() {
        // Указываем, какие поля из класса Computer брать для каждой колонки
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("cpuInfo"));
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ramGb"));

        // Сразу подгружаем данные при открытии окна
        onRefreshComputers();
    }

    @FXML
    public void onRefreshComputers() {
        computerTable.setItems(repository.getAllComputers());
    }

    @FXML
    public void onExitClick() {
        Platform.exit();
    }
}