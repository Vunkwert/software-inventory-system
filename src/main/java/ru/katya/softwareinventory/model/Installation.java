package ru.katya.softwareinventory.model;

import java.time.LocalDateTime;

public class Installation {
    private Long id;
    private Long computerId;
    private Long softwareId;
    private Long licenseId;
    private LocalDateTime installedAt;

    public Installation() {}

    public Long getId() { return id; }

    @Override
    public String toString() {
        return "Installation{" +
                "id=" + id +
                ", computerId=" + computerId +
                ", softwareId=" + softwareId +
                ", licenseId=" + licenseId +
                ", installedAt=" + installedAt +
                '}';
    }

    public void setId(Long id) { this.id = id; }

    public Long getComputerId() { return computerId; }
    public void setComputerId(Long computerId) { this.computerId = computerId; }

    public Long getSoftwareId() { return softwareId; }
    public void setSoftwareId(Long softwareId) { this.softwareId = softwareId; }

    public Long getLicenseId() { return licenseId; }
    public void setLicenseId(Long licenseId) { this.licenseId = licenseId; }

    public LocalDateTime getInstalledAt() { return installedAt; }
    public void setInstalledAt(LocalDateTime installedAt) { this.installedAt = installedAt; }
}
