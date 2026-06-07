package ru.katya.softwareinventory.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Репозиторий для работы с ПО через хранимые процедуры (п. 3.2 ТЗ).
 */
public class SoftwareRepository {

    /**
     * Вызывает хранимую процедуру для массовой установки ПО в аудитории.
     */
    public void installSoftwareToRoom(int roomId, int softwareId) {
        String sql = "{call add_software_to_room(?, ?)}";

        try {
            Connection conn = DatabaseManager.getConnection();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, roomId);
            stmt.setInt(2, softwareId);
            stmt.execute();
            stmt.close();
            System.out.println("Процедура успешно выполнена в БД");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}