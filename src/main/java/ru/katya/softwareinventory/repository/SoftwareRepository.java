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
    public javafx.collections.ObservableList<ru.katya.softwareinventory.model.Software> getAllSoftware() {
        javafx.collections.ObservableList<ru.katya.softwareinventory.model.Software> softwareList = javafx.collections.FXCollections.observableArrayList();
        String sql = "SELECT * FROM software ORDER BY name";

        try {
            java.sql.Connection conn = ru.katya.softwareinventory.repository.DatabaseManager.getConnection();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ru.katya.softwareinventory.model.Software s = new ru.katya.softwareinventory.model.Software();
                s.setId(rs.getLong("id"));
                s.setName(rs.getString("name"));
                s.setVersion(rs.getString("version"));
                s.setVendor(rs.getString("vendor"));
                softwareList.add(s);
            }
            rs.close();
            stmt.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return softwareList;
    }
}