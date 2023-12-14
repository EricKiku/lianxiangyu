package com.example.book_seat_reservation_sys_api.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    @TableId
    private int SId;

    private int SFloor;

    private String SCode;

    private int SState;

    private int SCanBeBooked;

    private String SInstruction;

    private String SCondition;


    public Seat() {
    }

    @Override
    public String toString() {
        return "Seat{" +
                "SId=" + SId +
                ", SFloor=" + SFloor +
                ", SCode='" + SCode + '\'' +
                ", SState=" + SState +
                ", SCanBeBooked=" + SCanBeBooked +
                ", SInstruction='" + SInstruction + '\'' +
                ", SCondition='" + SCondition + '\'' +
                '}';
    }

    @JsonProperty("SId")
    public int getSId() {
        return SId;
    }
    @JsonProperty("SId")
    public void setSId(int SId) {
        this.SId = SId;
    }
    @JsonProperty("SFloor")
    public int getSFloor() {
        return SFloor;
    }
    @JsonProperty("SFloor")
    public void setSFloor(int SFloor) {
        this.SFloor = SFloor;
    }
    @JsonProperty("SCode")
    public String getSCode() {
        return SCode;
    }
    @JsonProperty("SCode")
    public void setSCode(String SCode) {
        this.SCode = SCode;
    }
    @JsonProperty("SState")
    public int getSState() {
        return SState;
    }
    @JsonProperty("SState")
    public void setSState(int SState) {
        this.SState = SState;
    }
    @JsonProperty("SCanBeBooked")
    public int getSCanBeBooked() {
        return SCanBeBooked;
    }
    @JsonProperty("SCanBeBooked")
    public void setSCanBeBooked(int SCanBeBooked) {
        this.SCanBeBooked = SCanBeBooked;
    }
    @JsonProperty("SInstruction")
    public String getSInstruction() {
        return SInstruction;
    }
    @JsonProperty("SInstruction")
    public void setSInstruction(String SInstruction) {
        this.SInstruction = SInstruction;
    }
    @JsonProperty("SCondition")
    public String getSCondition() {
        return SCondition;
    }
    @JsonProperty("SCondition")
    public void setSCondition(String SCondition) {
        this.SCondition = SCondition;
    }
}
