package com.intigate.points;

/**
 * Created by ratnav on 18-04-2015.
 */
public class TransactionHistory_Handler


{

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    String date,time,company,points;
    boolean isUp;
}
