package com.gzn1ev.aramlejelentes;

public class Bill {
    private String id;
    private String title;
    private String price;
    private String date;
    private boolean isPaid;
    private String userId;

    public Bill(String id, String title, String price, String date, boolean isPaid, String userId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.date = date;
        this.isPaid = isPaid;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getUserId() {
        return userId;
    }
}
