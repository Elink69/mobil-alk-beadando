package com.gzn1ev.aramlejelentes;

import android.net.Uri;

public class Lejelentes {
    private String date;
    private float fogyasztas;
    private String userId;

    public Lejelentes() {
    }

    public Lejelentes(String date, float kWh, String userId) {
        this.date = date;
        this.fogyasztas = kWh;
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public float getFogyasztas() { return fogyasztas; }

    public String getUserId() {
        return userId;
    }
}




