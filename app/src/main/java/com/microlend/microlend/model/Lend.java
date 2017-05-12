package com.microlend.microlend.model;

import org.litepal.crud.DataSupport;

/**
 * Created by young on 2017/5/11.
 */

public class Lend extends DataSupport {

    private int id;
    private float money;
    private float sumMoney;
    private String loadPeopleName;
    private String loadPeopleIDCard;
    private String telPhone;
    private float rate;
    private String startDate;
    private int year;
    private int month;
    private int day;
    private String realLoadTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(float sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getLoadPeopleName() {
        return loadPeopleName;
    }

    public void setLoadPeopleName(String loadPeopleName) {
        this.loadPeopleName = loadPeopleName;
    }

    public String getLoadPeopleIDCard() {
        return loadPeopleIDCard;
    }

    public void setLoadPeopleIDCard(String loadPeopleIDCard) {
        this.loadPeopleIDCard = loadPeopleIDCard;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRealLoadTime() {
        return realLoadTime;
    }

    public void setRealLoadTime(String realLoadTime) {
        this.realLoadTime = realLoadTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
