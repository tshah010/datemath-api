package com.arihant.datemathapi.model;

public class APIError {
    private int id;
    private String message;

    public APIError(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
