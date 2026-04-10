package ru.katya.softwareinventory.model;

import java.time.LocalDateTime;

public class Computer {
    private Long id;
    private Long roomId;
    private String inventoryNumber;
    private String ipAddress;
    private String cpuInfo;
    private Integer ramGb;
    private LocalDateTime updatedAt;

    public Computer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getInventoryNumber() { return inventoryNumber; }
    public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getCpuInfo() { return cpuInfo; }
    public void setCpuInfo(String cpuInfo) { this.cpuInfo = cpuInfo; }

    public Integer getRamGb() { return ramGb; }
    public void setRamGb(Integer ramGb) { this.ramGb = ramGb; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}