package com.microlend.microlend.model;

import org.litepal.crud.DataSupport;

public class Lend extends DataSupport {

    private int id;
    private float money;
    private float sumMoney;
    private String loadPeopleName;
    private String loadPeopleIDCard;
    private String telPhone;
    private float rate;
    private String backDate;
    private int year;
    private int month;
    private int day;
    private String realLoadTime;
    private boolean back;

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


    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public float getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(float sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}
