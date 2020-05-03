package com.esmaeel.calendarlibrary;

public class EsAttrs {
    private Integer itemSize = 60; //sdp
    private Integer dayNumberTextSize = 20; //ssp
    private Integer dayNameTextSize = 10; //ssp
    private Integer monthNameTextSize = 10; //ssp

    /*md_grey_300*/
    private Integer normalDayNumberTextColor;
    /*white*/
    private Integer selectedDayNumberTextColor;
    /*md_grey_300*/
    private Integer normalDayNameTextColor;
    /*white*/
    private Integer selectedDayNameTextColor;
    /*md_grey_300*/
    private Integer normalMonthNameTextColor;
    /*white*/
    private Integer selectedMonthNameTextColor;
    /*white*/
    private Integer normalDayBackgroundColor;
    /*app_color*/
    private Integer selectedDayBackgroundColor;
    /*transparent*/
    private Integer calendarBackgroundColor;

    public EsAttrs() {
    }

    public EsAttrs(Integer itemSize, Integer dayNumberTextSize, Integer dayNameTextSize, Integer monthNameTextSize, Integer normalDayNumberTextColor, Integer selectedDayNumberTextColor, Integer normalDayNameTextColor, Integer selectedDayNameTextColor, Integer normalMonthNameTextColor, Integer selectedMonthNameTextColor, Integer normalDayBackgroundColor, Integer selectedDayBackgroundColor, Integer calendarBackgroundColor) {
        this.itemSize = itemSize;
        this.dayNumberTextSize = dayNumberTextSize;
        this.dayNameTextSize = dayNameTextSize;
        this.monthNameTextSize = monthNameTextSize;
        this.normalDayNumberTextColor = normalDayNumberTextColor;
        this.selectedDayNumberTextColor = selectedDayNumberTextColor;
        this.normalDayNameTextColor = normalDayNameTextColor;
        this.selectedDayNameTextColor = selectedDayNameTextColor;
        this.normalMonthNameTextColor = normalMonthNameTextColor;
        this.selectedMonthNameTextColor = selectedMonthNameTextColor;
        this.normalDayBackgroundColor = normalDayBackgroundColor;
        this.selectedDayBackgroundColor = selectedDayBackgroundColor;
        this.calendarBackgroundColor = calendarBackgroundColor;
    }

    public Integer getItemSize() {
        return itemSize;
    }

    public void setItemSize(Integer itemSize) {
        this.itemSize = itemSize;
    }

    public Integer getDayNumberTextSize() {
        return dayNumberTextSize;
    }

    public void setDayNumberTextSize(Integer dayNumberTextSize) {
        this.dayNumberTextSize = dayNumberTextSize;
    }

    public Integer getDayNameTextSize() {
        return dayNameTextSize;
    }

    public void setDayNameTextSize(Integer dayNameTextSize) {
        this.dayNameTextSize = dayNameTextSize;
    }

    public Integer getMonthNameTextSize() {
        return monthNameTextSize;
    }

    public void setMonthNameTextSize(Integer monthNameTextSize) {
        this.monthNameTextSize = monthNameTextSize;
    }

    public Integer getNormalDayNumberTextColor() {
        return normalDayNumberTextColor;
    }

    public void setNormalDayNumberTextColor(Integer normalDayNumberTextColor) {
        this.normalDayNumberTextColor = normalDayNumberTextColor;
    }

    public Integer getSelectedDayNumberTextColor() {
        return selectedDayNumberTextColor;
    }

    public void setSelectedDayNumberTextColor(Integer selectedDayNumberTextColor) {
        this.selectedDayNumberTextColor = selectedDayNumberTextColor;
    }

    public Integer getNormalDayNameTextColor() {
        return normalDayNameTextColor;
    }

    public void setNormalDayNameTextColor(Integer normalDayNameTextColor) {
        this.normalDayNameTextColor = normalDayNameTextColor;
    }

    public Integer getSelectedDayNameTextColor() {
        return selectedDayNameTextColor;
    }

    public void setSelectedDayNameTextColor(Integer selectedDayNameTextColor) {
        this.selectedDayNameTextColor = selectedDayNameTextColor;
    }

    public Integer getNormalMonthNameTextColor() {
        return normalMonthNameTextColor;
    }

    public void setNormalMonthNameTextColor(Integer normalMonthNameTextColor) {
        this.normalMonthNameTextColor = normalMonthNameTextColor;
    }

    public Integer getSelectedMonthNameTextColor() {
        return selectedMonthNameTextColor;
    }

    public void setSelectedMonthNameTextColor(Integer selectedMonthNameTextColor) {
        this.selectedMonthNameTextColor = selectedMonthNameTextColor;
    }

    public Integer getNormalDayBackgroundColor() {
        return normalDayBackgroundColor;
    }

    public void setNormalDayBackgroundColor(Integer normalDayBackgroundColor) {
        this.normalDayBackgroundColor = normalDayBackgroundColor;
    }

    public Integer getSelectedDayBackgroundColor() {
        return selectedDayBackgroundColor;
    }

    public void setSelectedDayBackgroundColor(Integer selectedDayBackgroundColor) {
        this.selectedDayBackgroundColor = selectedDayBackgroundColor;
    }

    public Integer getCalendarBackgroundColor() {
        return calendarBackgroundColor;
    }

    public void setCalendarBackgroundColor(Integer calendarBackgroundColor) {
        this.calendarBackgroundColor = calendarBackgroundColor;
    }

    /*
     * returns the item size / 2 to get the perfect circle.
     * */
    public float getRadius() {
        return getItemSize() == null ? 40f : (getItemSize() / 2);
    }
}