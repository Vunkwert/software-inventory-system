package ru.katya.softwareinventory.model;

public class Room {
    private Long id;
    private String number;
    private String description;

    public Room() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
