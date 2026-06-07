package ru.katya.softwareinventory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.katya.softwareinventory.model.Computer;

public class MainController {
    @FXML private TableView<Computer> computerTable;
    @FXML private TableColumn<Computer, Long> idCol;
    @FXML private TableColumn<Computer, String> invCol;
    @FXML private TableColumn<Computer, String> ipCol;
    @FXML private TableColumn<Computer, String> cpuCol;
    @FXML private TableColumn<Computer, Integer> ramCol;

    @FXML
    public void onRefreshComputers() {
        // Здесь мы позже напишем код загрузки данных из БД
        System.out.println("Обновление списка компьютеров...");
    }

    @FXML
    public void onExitClick() {
        Platform.exit();
    }
}