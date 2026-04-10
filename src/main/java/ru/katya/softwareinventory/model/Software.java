package ru.katya.softwareinventory.model;

public class Software {
    private Long id;
    private String name;
    private String version;
    private Long categoryId;
    private String vendor;

    public Software() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Software{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", categoryId=" + categoryId +
                ", vendor='" + vendor + '\'' +
                '}';
    }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }
}
