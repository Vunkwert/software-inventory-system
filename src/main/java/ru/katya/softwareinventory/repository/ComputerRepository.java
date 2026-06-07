package ru.katya.softwareinventory.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.katya.softwareinventory.model.Computer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComputerRepository {

    public ObservableList<Computer> getAllComputers() {
        ObservableList<Computer> computers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM computers ORDER BY id";

        try {
            Connection conn = DatabaseManager.getConnection(); // Просто берем, не закрываем
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Computer pc = new Computer();
                pc.setId(rs.getLong("id"));
                pc.setInventoryNumber(rs.getString("inventory_number"));
                pc.setIpAddress(rs.getString("ip_address"));
                pc.setCpuInfo(rs.getString("cpu_info"));
                pc.setRamGb(rs.getInt("ram_gb"));
                computers.add(pc);
            }
            // Закрываем только временные объекты
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computers;
    }
}