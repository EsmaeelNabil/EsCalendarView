package com.esmaeel.calendarlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.common.CalendarFactory;
import com.aminography.primecalendar.common.CalendarType;
import com.esmaeel.calendarlibrary.databinding.CalendarViewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class EsCalendarView extends LinearLayout {
    private Context mContext;
    EsCalendarListener esCalendarListener;

    private Integer itemSize = 60; // sdp

    CalendarViewBinding binder;
    DatesAdapter adapter;
    ArrayList<DateModel> datesList = new ArrayList<>();
    private Integer daysCount = 120; /* Days of the calendar by default */ // TODO: 5/2/20 make a getter and setter for it

    public EsCalendarView(Context mContext) {
        super(mContext);
    }

    EsAttrs esAttrs;
    boolean includeToday = false;

    protected Integer previousMonthTextColor; /*md_blue_800*/
    protected Integer currentMonthTextColor; /*black*/
    protected Integer previousMonthTextSize = 14; //ssp
    protected Integer currentMonthTextSize = 14; //ssp
    protected Integer dayNumberTextSize = 20; //ssp
    protected Integer dayNameTextSize = 10; //ssp
    protected Integer monthNameTextSize = 10; //ssp

    /*md_grey_300*/
    protected Integer normalDayNumberTextColor;
    /*white*/
    protected Integer selectedDayNumberTextColor;
    /*md_grey_300*/
    protected Integer normalDayNameTextColor;
    /*white*/
    protected Integer selectedDayNameTextColor;
    /*md_grey_300*/
    protected Integer normalMonthNameTextColor;
    /*white*/
    protected Integer selectedMonthNameTextColor;

    /*white*/
    protected Integer normalDayBackgroundColor;
    /*app_color*/
    protected Integer selectedDayBackgroundColor;
    /*transparent*/
    protected Integer calendarBackgroundColor;

    public void setOnDateSelectedListener(EsCalendarListener esCalendarListener) {
        this.esCalendarListener = esCalendarListener;
    }


    public EsCalendarView(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;

        if (isInEditMode())
            return;

        bind(); // bind views
        initializeCalendar();

        getAttributes(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        binder = null;
    }


    private void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EsCalendarView, 0, 0);

        includeToday = typedArray.getBoolean(R.styleable.EsCalendarView_includeToday, false);

        daysCount = (int) typedArray.getDimension(R.styleable.EsCalendarView_daysCount,daysCount);

        previousMonthTextColor = typedArray.getColor(R.styleable.EsCalendarView_previousMonthTextColor, getResources().getColor(R.color.black));
        if (binder != null)
            binder.prevMonth.setTextColor(previousMonthTextColor);

        previousMonthTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_previousMonthTextSize, previousMonthTextSize);
        if (binder != null)
            binder.prevMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, previousMonthTextSize);

        currentMonthTextColor = typedArray.getColor(R.styleable.EsCalendarView_currentMonthTextColor, getResources().getColor(R.color.md_blue_600));
        if (binder != null)
            binder.currentMonth.setTextColor(currentMonthTextColor);

        currentMonthTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_previousMonthTextSize, currentMonthTextSize);
        if (binder != null)
            binder.currentMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, currentMonthTextSize);

        // TODO: 5/2/20 pass to adapter and notify changes to it.
        itemSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_itemSize, itemSize);
        esAttrs.setItemSize(itemSize);

        dayNumberTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_dayNumberTextSize, dayNumberTextSize);
        esAttrs.setDayNumberTextSize(dayNumberTextSize);

        dayNameTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_dayNameTextSize, dayNameTextSize);
        esAttrs.setDayNameTextSize(dayNameTextSize);

        monthNameTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_monthNameTextSize, monthNameTextSize);
        esAttrs.setMonthNameTextSize(monthNameTextSize);

        /*md_grey_300*/

        normalDayNumberTextColor = typedArray.getColor(R.styleable.EsCalendarView_normalDayNumberTextColor, ContextCompat.getColor(mContext, R.color.md_grey_300));
        esAttrs.setNormalDayNumberTextColor(normalDayNumberTextColor);

        /*white*/
        selectedDayNumberTextColor = typedArray.getColor(R.styleable.EsCalendarView_selectedDayNumberTextColor, ContextCompat.getColor(mContext, R.color.white));
        esAttrs.setSelectedDayNumberTextColor(selectedDayNumberTextColor);


        /*md_grey_300*/
        normalDayNameTextColor = typedArray.getColor(R.styleable.EsCalendarView_normalDayNameTextColor, ContextCompat.getColor(mContext, R.color.md_grey_300));
        esAttrs.setNormalDayNameTextColor(normalDayNameTextColor);

        /*white*/
        selectedDayNameTextColor = typedArray.getColor(R.styleable.EsCalendarView_selectedDayNameTextColor, ContextCompat.getColor(mContext, R.color.white));
        esAttrs.setSelectedDayNameTextColor(selectedDayNameTextColor);

        /*md_grey_300*/
        normalMonthNameTextColor = typedArray.getColor(R.styleable.EsCalendarView_normalMonthNameTextColor, ContextCompat.getColor(mContext, R.color.md_grey_300));
        esAttrs.setNormalMonthNameTextColor(normalMonthNameTextColor);


        /*white*/
        selectedMonthNameTextColor = typedArray.getColor(R.styleable.EsCalendarView_selectedMonthNameTextColor, ContextCompat.getColor(mContext, R.color.white));
        esAttrs.setSelectedMonthNameTextColor(selectedMonthNameTextColor);

        /*white*/
        normalDayBackgroundColor = typedArray.getColor(R.styleable.EsCalendarView_normalDayBackgroundColor, ContextCompat.getColor(mContext, R.color.white));
        esAttrs.setNormalDayBackgroundColor(normalDayBackgroundColor);

        /*app_color*/
        selectedDayBackgroundColor = typedArray.getColor(R.styleable.EsCalendarView_selectedDayBackgroundColor, ContextCompat.getColor(mContext, R.color.app_color));
        esAttrs.setSelectedDayBackgroundColor(selectedDayBackgroundColor);

        /*transparent*/
        calendarBackgroundColor = typedArray.getColor(R.styleable.EsCalendarView_calendarBackgroundColor, Color.TRANSPARENT);
        if (binder != null)
            binder.getRoot().setBackgroundColor(calendarBackgroundColor);

        esAttrs.setCalendarBackgroundColor(calendarBackgroundColor);

        if (esAttrs != null)
            adapter.reDraw(esAttrs);

        typedArray.recycle();
    }


    private DateModel selectedModel;
    private int selectedPosition;

    private void initializeCalendar() {

        binder.datesRecycler.setHasFixedSize(true);
        binder.datesRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));
        adapter = new DatesAdapter();
        binder.datesRecycler.setAdapter(adapter);


        adapter.setOnDateSelectedListener((model, position) -> {
            selectedModel = model;
            binder.prevMonth.setText(model.getPrevMonth());
            binder.currentMonth.setText(model.getCurrentMonth());
            if (esCalendarListener != null)
                esCalendarListener.onDateSelectedListener(model, position);
        });

        /*Listen for scrolling to get the current and previous data depending on the showing items*/
        RecyclerViewPositionHelper helper = new RecyclerViewPositionHelper(binder.datesRecycler);
        ViewTreeObserver observer = binder.datesRecycler.getViewTreeObserver();
        observer.addOnScrollChangedListener(() -> {
            if (datesList != null)
                if (!datesList.isEmpty()) {
                    /*if the selected date is visible, then update views according to it */
                    if (getVisiblePositions(helper).contains(adapter.getSelectedPosition())) {
                        binder.currentMonth.setText(datesList.get(adapter.getSelectedPosition()).getCurrentMonth());
                        binder.prevMonth.setText(datesList.get(adapter.getSelectedPosition()).getPrevMonth());
                    } else {
                        /* the selected date is NOT visible, so we update views according to first item visible */
                        binder.currentMonth.setText(datesList.get(helper.findFirstVisibleItemPosition()).getCurrentMonth());
                        binder.prevMonth.setText(datesList.get(helper.findFirstVisibleItemPosition()).getPrevMonth());
                    }
                }
        });

        fillData(daysCount);
    }


    private void bind() {
        binder = CalendarViewBinding.inflate((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), this, true);
        esAttrs = new EsAttrs();
    }

    public void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount == null ? 120 : daysCount;
        postInvalidate();
    }


    private void fillData(Integer dateCounts) {
        PrimeCalendar todayCalendar = CalendarFactory.newInstance(CalendarType.CIVIL, Locale.ENGLISH);
        Date date = new Date();
        todayCalendar.setTime(date);

        for (Integer count = 0; count < 120; count++) {

            // include today in the calendar
            if (includeToday) {
                if (count != 0) // skipp first day from incrementing to include it
                    todayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                todayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            todayCalendar.add(Calendar.MONTH, 0);
            todayCalendar.add(Calendar.YEAR, 0);

            datesList.add(
                    new DateModel(
                            count,
                            todayCalendar,
                            todayCalendar.getWeekDayNameShort(),
                            getDayWithZero(todayCalendar),
                            getCurrentMonth(todayCalendar),
                            getPrevMonth(todayCalendar),
                            count == 0   /* Means if today then select it */,
                            todayCalendar.getShortDateString()
                    )
            );
        }

        Timber.e(datesList.toString());
        adapter.setDatesList(datesList);
    }

    private String getPrevMonth(PrimeCalendar todayCalendar) {
        PrimeCalendar prevCalendar = CalendarFactory.newInstance(CalendarType.CIVIL, Locale.ENGLISH);
        prevCalendar.setTime(todayCalendar.getTime());
        prevCalendar.roll(Calendar.MONTH, false);


        return prevCalendar.getMonthName() + " " + prevCalendar.getYear();
    }

    private String getCurrentMonth(PrimeCalendar todayCalendar) {
        return todayCalendar.getMonthName() + " " + todayCalendar.getYear();
    }

    @NotNull /* if the day is like that -> 9 becomes like this 09 */
    private String getDayWithZero(PrimeCalendar civilCalendar) {
        return String.valueOf(civilCalendar.getDayOfMonth()).length() == 1 ? "0" +
                civilCalendar.getDayOfMonth()
                : String.valueOf(civilCalendar.getDayOfMonth());
    }

    @NotNull
    private ArrayList<Integer> getVisiblePositions(RecyclerViewPositionHelper helper) {
        Integer first = helper.findFirstVisibleItemPosition();
        Integer last = helper.findLastVisibleItemPosition();
        Integer count = (last - first) + 1; // one for the zero at first

        ArrayList<Integer> positionsList = new ArrayList<>();
        for (Integer i = first; i <= last; i++) {
            positionsList.add(i);
        }
        return positionsList;
    }

    @Deprecated
    private DateModel getSelectedCalendar() {
        return selectedModel;
    }




    protected static class EsAttrs {
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


}
