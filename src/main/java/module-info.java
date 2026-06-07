module ru.katya.softwareinventory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ru.katya.softwareinventory to javafx.fxml;
    opens ru.katya.softwareinventory.model to javafx.base;
    exports ru.katya.softwareinventory;
}