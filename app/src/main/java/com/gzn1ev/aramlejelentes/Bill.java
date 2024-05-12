package com.gzn1ev.aramlejelentes;

import androidx.annotation.Nullable;

public class Bill {
    private String mId;
    private String title;
    private int price;
    private String date;
    @Nullable
    private String paidDate;
    private boolean isPaid;
    private String userId;

    public Bill() {}

    public Bill(String mId, String title, int price, String date, @Nullable String paidDate, boolean isPaid, String userId) {
        this.mId = mId;
        this.title = title;
        this.price = price;
        this.date = date;
        this.paidDate = paidDate;
        this.isPaid = isPaid;
        this.userId = userId;
    }

    public String getMId() {
        return mId;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getPaidDate() { return paidDate; }

    public boolean isPaid() {
        return isPaid;
    }

    public String getUserId() {
        return userId;
    }

    public void setMId(String id) {
        this.mId = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
