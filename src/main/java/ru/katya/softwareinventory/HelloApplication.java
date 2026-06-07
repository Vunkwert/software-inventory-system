package ru.katya.softwareinventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("auth-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Авторизация - Учет ПО");
        stage.setScene(scene);
        stage.setResizable(false); // Запретим менять размер окна входа
        stage.show();
    }
}
