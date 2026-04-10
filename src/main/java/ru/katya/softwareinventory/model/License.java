package ru.katya.softwareinventory.model;

import java.time.LocalDate;

public class License {
    private Long id;
    private Long softwareId;
    private String licenseKey;
    private String licenseType;
    private LocalDate expiryDate;

    public License() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return "License{" +
                "id=" + id +
                ", softwareId=" + softwareId +
                ", licenseKey='" + licenseKey + '\'' +
                ", licenseType='" + licenseType + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public Long getSoftwareId() { return softwareId; }
    public void setSoftwareId(Long softwareId) { this.softwareId = softwareId; }

    public String getLicenseKey() { return licenseKey; }
    public void setLicenseKey(String licenseKey) { this.licenseKey = licenseKey; }

    public String getLicenseType() { return licenseType; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
