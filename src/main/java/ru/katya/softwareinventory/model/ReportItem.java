package ru.katya.softwareinventory.model;

/**
 * Класс для отображения одной строки любого отчета.
 */
public class ReportItem {
    private String room;
    private String software;
    private String version;
    private String pcInventory;

    public ReportItem(String room, String software, String version, String pcInventory) {
        this.room = room;
        this.software = software;
        this.version = version;
        this.pcInventory = pcInventory;
    }

    // Геттеры (обязательны для TableView)
    public String getRoom() { return room; }
    public String getSoftware() { return software; }
    public String getVersion() { return version; }
    public String getPcInventory() { return pcInventory; }
}