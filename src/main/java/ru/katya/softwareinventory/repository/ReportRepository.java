package ru.katya.softwareinventory.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.katya.softwareinventory.model.ReportItem;
import ru.katya.softwareinventory.AppLogger;

import java.sql.*;

public class ReportRepository {

    public ObservableList<ReportItem> generateSoftwareReport(String queryKey, String param) {
        ObservableList<ReportItem> report = FXCollections.observableArrayList();
        String sql = DatabaseManager.getQuery(queryKey);

        try {
            // Берем соединение, но НЕ используем try-with-resources для него!
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, param);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                report.add(new ReportItem(
                        rs.getString("room"),
                        rs.getString("soft"),
                        rs.getString("version"),
                        rs.getString("pc")
                ));
            }

            // Закрываем только временные объекты (курсоры)
            rs.close();
            pstmt.close();

            AppLogger.info("Сформирован отчет [" + queryKey + "] по параметру: " + param);
        } catch (SQLException e) {
            AppLogger.info("ОШИБКА отчета: " + e.getMessage());
            e.printStackTrace();
        }
        return report;
    }
}