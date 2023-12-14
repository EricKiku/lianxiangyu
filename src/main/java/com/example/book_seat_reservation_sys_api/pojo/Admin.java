package com.example.book_seat_reservation_sys_api.pojo;

public class Admin {
    private int AId;
    private String AName;
    private String AAccess;
    private String APwd;

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "AId=" + AId +
                ", AName='" + AName + '\'' +
                ", AAccess='" + AAccess + '\'' +
                ", APwd='" + APwd + '\'' +
                '}';
    }

    public int getAId() {
        return AId;
    }

    public void setAId(int AId) {
        this.AId = AId;
    }

    public String getAName() {
        return AName;
    }

    public void setAName(String AName) {
        this.AName = AName;
    }

    public String getAAccess() {
        return AAccess;
    }

    public void setAAccess(String AAccess) {
        this.AAccess = AAccess;
    }

    public String getAPwd() {
        return APwd;
    }

    public void setAPwd(String APwd) {
        this.APwd = APwd;
    }
}

