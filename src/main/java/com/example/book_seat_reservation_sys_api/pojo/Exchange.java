package com.example.book_seat_reservation_sys_api.pojo;

public class Exchange {
    private int EId;
    private String EName;
    private int EPoints;
    private String EImg;
    private String EInventory;
    private String EDescription;
    private String EType;

    public Exchange() {
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "EId=" + EId +
                ", EName='" + EName + '\'' +
                ", EPoints=" + EPoints +
                ", EImg='" + EImg + '\'' +
                ", EInventory='" + EInventory + '\'' +
                ", EDescription='" + EDescription + '\'' +
                ", EType='" + EType + '\'' +
                '}';
    }

    public int getEId() {
        return EId;
    }

    public void setEId(int EId) {
        this.EId = EId;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public int getEPoints() {
        return EPoints;
    }

    public void setEPoints(int EPoints) {
        this.EPoints = EPoints;
    }

    public String getEImg() {
        return EImg;
    }

    public void setEImg(String EImg) {
        this.EImg = EImg;
    }

    public String getEInventory() {
        return EInventory;
    }

    public void setEInventory(String EInventory) {
        this.EInventory = EInventory;
    }

    public String getEDescription() {
        return EDescription;
    }

    public void setEDescription(String EDescription) {
        this.EDescription = EDescription;
    }

    public String getEType() {
        return EType;
    }

    public void setEType(String EType) {
        this.EType = EType;
    }
}
