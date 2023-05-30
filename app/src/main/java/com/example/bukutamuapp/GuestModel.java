package com.example.bukutamuapp;

public class GuestModel {
    private int id;
    private String name;
    private int status;

    public GuestModel() {
        // kosongkan konstruktor
    }

    public GuestModel(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return " Nama Tamu: " + name + ", Status: " + getStatusAsString();
    }

    private String getStatusAsString() {
        switch (status) {
            case 0:
                return "Belum Datang";
            case 1:
                return "Datang";
            case 2:
                return "Berhalangan Hadir";
            default:
                return "Tidak Diketahui";
        }
    }
}
