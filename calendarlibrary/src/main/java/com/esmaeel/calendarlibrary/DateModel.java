package com.esmaeel.calendarlibrary;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;

import java.util.Locale;

public class DateModel {
    private Integer uniqeId;
    private PrimeCalendar date;
    private String name;
    private String number;
    private String currentMonth;
    private String prevMonth;
    private Boolean selected = false;
    private String apiDate;

    public Boolean getSelected() {
        return selected;
    }

    public String getApiDate() {
        return apiDate;
    }

    public void setApiDate(String apiDate) {
        this.apiDate = apiDate;
    }

    @Override
    public String toString() {
        return "DateModel{" +
               "uniqeId=" + uniqeId +
               ", date=" + date +
               ", name='" + name + '\'' +
               ", number='" + number + '\'' +
               ", currentMonth='" + currentMonth + '\'' +
               ", prevMonth='" + prevMonth + '\'' +
               ", selected=" + selected +
               ", apiDate='" + apiDate + '\'' +
               '}';
    }

    public Integer getUniqeId() {
        return uniqeId;
    }

    public void setUniqeId(Integer uniqeId) {
        this.uniqeId = uniqeId;
    }

    public PrimeCalendar getDate() {
        return date;
    }

    public void setDate(PrimeCalendar date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public String getPrevMonth() {
        return prevMonth;
    }

    public void setPrevMonth(String prevMonth) {
        this.prevMonth = prevMonth;
    }

    public Boolean isSelected() {
        return selected == null ? false : selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public DateModel() {
    }

    public DateModel(PrimeCalendar date) {
        this.date = date;
    }

    public DateModel getDummyMe(){
        return new DateModel(CalendarFactory.newInstance(CalendarType.CIVIL, Locale.ENGLISH));
    }

    public DateModel(Integer uniqeId, PrimeCalendar date, String name, String number, String currentMonth, String prevMonth, Boolean selected, String apiDate) {
        this.uniqeId = uniqeId;
        this.date = date;
        this.name = name;
        this.number = number;
        this.currentMonth = currentMonth;
        this.prevMonth = prevMonth;
        this.selected = selected;
        this.apiDate = apiDate;
    }
}
