module ru.katya.softwareinventory {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.katya.softwareinventory to javafx.fxml;
    exports ru.katya.softwareinventory;
}