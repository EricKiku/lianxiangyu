package com.example.book_seat_reservation_sys_api.pojo;

public class Student {
    private int StuId;
    private String StuNumber;
    private String StuName;
    private String StuPwd;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "StuId=" + StuId +
                ", StuNumber='" + StuNumber + '\'' +
                ", StuName='" + StuName + '\'' +
                ", StuPwd='" + StuPwd + '\'' +
                '}';
    }

    public int getStuId() {
        return StuId;
    }

    public void setStuId(int stuId) {
        StuId = stuId;
    }

    public String getStuNumber() {
        return StuNumber;
    }

    public void setStuNumber(String stuNumber) {
        StuNumber = stuNumber;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getStuPwd() {
        return StuPwd;
    }

    public void setStuPwd(String stuPwd) {
        StuPwd = stuPwd;
    }
}
