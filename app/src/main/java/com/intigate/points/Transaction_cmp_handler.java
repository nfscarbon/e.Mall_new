package com.intigate.points;

/**
 * Created by ratnav on 26-05-2015.
 */
public class Transaction_cmp_handler {


    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    int Points;
    String CompanyName;
    String url;
}
