package ru.katya.softwareinventory.model;

import java.time.LocalDateTime;

public class AuditLog {
    private Long id;
    private String userName;
    private String operation;
    private String tableName;

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", operation='" + operation + '\'' +
                ", tableName='" + tableName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private LocalDateTime createdAt;

    public AuditLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
