package com.example.book_seat_reservation_sys_api.pojo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = {"RId","SId","StuId","RStartTime","REndTime","REnterTime","RLraveTime","RDate","RState","RComplete"})
public class Reserecord {
    private int RId;
    private int SId;
    private String StuId;
    private String RStartTime;
    private String REndTime;
    private String REnterTime;
    private String RLeaveTime;
    private String RDate;
    private int RState;
    private String RComplete;

    public Reserecord() {
    }

    public String getrComplete() {
        return RComplete;
    }

    public void setrComplete(String RComplete) {
        this.RComplete = RComplete;
    }

    @Override
    public String toString() {
        return "Reserecord{" +
                "RId=" + RId +
                ", SId=" + SId +
                ", StuId='" + StuId + '\'' +
                ", RStartTime='" + RStartTime + '\'' +
                ", REndTime='" + REndTime + '\'' +
                ", REnterTime='" + REnterTime + '\'' +
                ", RLeaveTime='" + RLeaveTime + '\'' +
                ", RDate='" + RDate + '\'' +
                ", RState=" + RState +
                ", rComplete='" + RComplete + '\'' +
                '}';
    }

    public int getRId() {
        return RId;
    }

    public void setRId(int RId) {
        this.RId = RId;
    }

    public int getSId() {
        return SId;
    }

    public void setSId(int SId) {
        this.SId = SId;
    }

    public String getStuId() {
        return StuId;
    }

    public void setStuId(String stuId) {
        StuId = stuId;
    }

    public String getRStartTime() {
        return RStartTime;
    }

    public void setRStartTime(String RStartTime) {
        this.RStartTime = RStartTime;
    }

    public String getREndTime() {
        return REndTime;
    }

    public void setREndTime(String REndTime) {
        this.REndTime = REndTime;
    }

    public String getREnterTime() {
        return REnterTime;
    }

    public void setREnterTime(String REnterTime) {
        this.REnterTime = REnterTime;
    }

    public String getRLeaveTime() {
        return RLeaveTime;
    }

    public void setRLeaveTime(String RLeaveTime) {
        this.RLeaveTime = RLeaveTime;
    }

    public String getRDate() {
        return RDate;
    }

    public void setRDate(String RDate) {
        this.RDate = RDate;
    }

    public int getRState() {
        return RState;
    }

    public void setRState(int RState) {
        this.RState = RState;
    }
}
