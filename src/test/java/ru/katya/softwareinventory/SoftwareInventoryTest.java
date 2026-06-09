package ru.katya.softwareinventory;

import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import java.util.Locale;
import static org.testfx.api.FxAssert.verifyThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SoftwareInventoryTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Фиксируем русский язык для тестов
        Locale.setDefault(Locale.forLanguageTag("ru"));
        new HelloApplication().start(stage);
    }

    @Test
    @Order(1)
    @DisplayName("Положительный: Успешная авторизация")
    void testLoginSuccess() {
        // Пишем данные и жмем на ID кнопки
        clickOn("#usernameField").write("postgres");
        clickOn("#passwordField").write("postgres");
        clickOn("#loginBtn");

        sleep(1000);
        // Проверяем, что главная таблица появилась
        verifyThat("#computerTable", NodeMatchers.isVisible());
    }

    @Test
    @Order(2)
    @DisplayName("Отрицательный: Вход с неверным паролем")
    void testLoginFailure() {
        doubleClickOn("#usernameField").write("admin");
        doubleClickOn("#passwordField").write("wrong_pass");
        clickOn("#loginBtn");

        verifyThat("#errorLabel", LabeledMatchers.hasText("Invalid login or password / Неверный вход"));
    }

    @Test
    @Order(3)
    @DisplayName("Положительный: Открытие окна установки ПО")
    void testOpenInstallWindow() {
        testLoginSuccess(); // Входим

        clickOn("#addSoftBtn"); // Жмем на ID кнопки

        // Проверяем, что поле ввода в новом окне появилось
        verifyThat("#softwareIdField", NodeMatchers.isVisible());
    }

    @Test
    @Order(4)
    @DisplayName("Отрицательный: Валидация пустого ввода")
    void testInstallValidation() {
        testOpenInstallWindow(); // Открываем окно

        // 1. Очищаем поля (кликаем и несколько раз жмем Backspace)
        clickOn("#softwareIdField").type(javafx.scene.input.KeyCode.BACK_SPACE, 10);
        clickOn("#roomIdField").type(javafx.scene.input.KeyCode.BACK_SPACE, 10);

        // 2. Жмем на кнопку запуска по её ID
        clickOn("#runInstallBtn");

        // 3. Ждем появления окна ошибки
        sleep(1000);

        // 4. Проверяем наличие кнопки "OK".
        // В JavaFX Алерты всегда содержат кнопку с текстом "OK" (латиницей)
        try {
            verifyThat("OK", NodeMatchers.isVisible());
            clickOn("OK"); // Закрываем, чтобы не мешать
        } catch (Exception e) {
            // Если "OK" не нашлось, попробуем русское "ОК"
            verifyThat("ОК", NodeMatchers.isVisible());
            clickOn("ОК");
        }

        AppLogger.info("Тест валидации успешно прошел: окно ошибки обнаружено и закрыто.");
    }
}