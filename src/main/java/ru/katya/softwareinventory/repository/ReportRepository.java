package ru.katya.softwareinventory.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.katya.softwareinventory.model.AuditLog;

import java.sql.*;

public class ReportRepository {
    public ObservableList<AuditLog> getAuditLogs() {
        ObservableList<AuditLog> logs = FXCollections.observableArrayList();
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC";

        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setId(rs.getLong("id"));
                log.setUserName(rs.getString("user_name"));
                log.setOperation(rs.getString("operation"));
                log.setTableName(rs.getString("table_name"));
                log.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                logs.add(log);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}