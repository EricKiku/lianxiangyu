package com.example.book_seat_reservation_sys_api.pojo;

public class Points {
    private  int PId;
    private  int StuId;

    private int PAmount;

    public Points() {
    }

    public int getPId() {
        return PId;
    }

    public void setPId(int PId) {
        this.PId = PId;
    }

    public int getStuId() {
        return StuId;
    }

    public void setStuId(int stuId) {
        StuId = stuId;
    }

    public int getPAmount() {
        return PAmount;
    }

    public void setPAmount(int PAmount) {
        this.PAmount = PAmount;
    }
}
