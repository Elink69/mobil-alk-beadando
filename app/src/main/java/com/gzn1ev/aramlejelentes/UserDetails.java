package com.gzn1ev.aramlejelentes;

public class UserDetails {
    private String fullName;
    private String email;
    private String registrationDate;
    private String meroOraId;

    public UserDetails() {
    }

    public UserDetails(String name, String email, String registrationDate, String meroOraId) {
        this.fullName = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.meroOraId = meroOraId;
    }

    public String getFullName() { return fullName; }

    public String getEmail() { return email; }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getMeroOraId() {
        return meroOraId;
    }
}
