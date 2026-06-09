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
    public void installSoftwareToRoom(String roomNumber, int softwareId) { // roomNumber теперь String
        String sql = DatabaseManager.getQuery("sql.proc.installSoftware");

        try {
            java.sql.Connection conn = DatabaseManager.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, roomNumber); // Используем setString
            pstmt.setInt(2, softwareId);
            pstmt.execute();
            pstmt.close();
        } catch (java.sql.SQLException e) {
            // Пробрасываем ошибку дальше, чтобы контроллер её поймал
            throw new RuntimeException(e.getMessage());
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
    public javafx.collections.ObservableList<ru.katya.softwareinventory.model.Software> getSoftwareForComputer(Long computerId) {
        javafx.collections.ObservableList<ru.katya.softwareinventory.model.Software> list = javafx.collections.FXCollections.observableArrayList();
        String sql = DatabaseManager.getQuery("sql.computers.getSoftware");

        try {
            java.sql.Connection conn = DatabaseManager.getConnection();
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, computerId);
            java.sql.ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ru.katya.softwareinventory.model.Software s = new ru.katya.softwareinventory.model.Software();
                s.setId(rs.getLong("id"));
                s.setName(rs.getString("name"));
                s.setVersion(rs.getString("version"));
                s.setVendor(rs.getString("vendor"));
                list.add(s);
            }
            rs.close();
            pstmt.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}