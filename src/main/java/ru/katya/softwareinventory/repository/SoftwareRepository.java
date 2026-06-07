package ru.katya.softwareinventory.repository;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Репозиторий для работы с ПО через хранимые процедуры (п. 3.2 ТЗ).
 */
public class SoftwareRepository {

    /**
     * Вызывает хранимую процедуру для массовой установки ПО в аудитории.
     */
    public void installSoftwareToRoom(int roomId, int softwareId) {
        // Синтаксис вызова процедуры: {call имя_процедуры(?, ?)}
        String sql = "{call add_software_to_room(?, ?)}";

        try (CallableStatement stmt = DatabaseManager.getConnection().prepareCall(sql)) {
            stmt.setInt(1, roomId);
            stmt.setInt(2, softwareId);
            stmt.execute();
            System.out.println("Процедура успешно выполнена в БД");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}