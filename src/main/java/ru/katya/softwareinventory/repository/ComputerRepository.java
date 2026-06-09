package ru.katya.softwareinventory.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.katya.softwareinventory.AppLogger;
import ru.katya.softwareinventory.model.Computer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComputerRepository {

    public ObservableList<Computer> getAllComputers() {
        ObservableList<Computer> computers = FXCollections.observableArrayList();

        // БЕРЕМ ЗАПРОС ИЗ ФАЙЛА (там где JOIN с аудиториями)
        String sql = DatabaseManager.getQuery("sql.computers.getAll");

        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Computer pc = new Computer();
                pc.setId(rs.getLong("id"));
                pc.setInventoryNumber(rs.getString("inventory_number"));
                pc.setIpAddress(rs.getString("ip_address"));
                pc.setCpuInfo(rs.getString("cpu_info"));
                pc.setRamGb(rs.getInt("ram_gb"));
                pc.setEmployeeName(rs.getString("emp_name"));

                // Теперь эта колонка точно будет в результате!
                pc.setRoomNumber(rs.getString("room_number"));

                computers.add(pc);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogger.info("ОШИБКА загрузки ПК: " + e.getMessage());
            e.printStackTrace();
        }
        return computers;
    }
}