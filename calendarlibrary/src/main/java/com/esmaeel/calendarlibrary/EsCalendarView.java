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
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

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
    boolean arabicSupport = true;

    boolean hidePreviousMonth = false;
    boolean hideCurrentMonth = false;

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
    protected String dateSplitChar = "-"; /* default split for Dates */

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

        populateDates(daysCount);
    }

    private void populateDates(Integer daysCount) {
        Single.just(getDates(daysCount))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<DateModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(ArrayList<DateModel> dateModels) {
//                        Log.e(TAG, "onSuccess: : " + datesList.toString());
                        adapter.setDatesList(datesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        binder = null;
    }

    private static final String TAG = "EsCalendarView";

    private void getAttributes(AttributeSet attrs) {
        final TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EsCalendarView, 0, 0);

        /*date split -> 2020/5/4  ---> 2020-5-4*/
        String splitter = typedArray.getString(R.styleable.EsCalendarView_dateSplitChar);
        this.dateSplitChar = isValid(splitter) ? splitter : dateSplitChar;

        this.includeToday = typedArray.getBoolean(R.styleable.EsCalendarView_includeToday, false);
        this.arabicSupport = typedArray.getBoolean(R.styleable.EsCalendarView_arabicSupport, true);
        if (!arabicSupport) {
            this.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            if (binder != null) {
                binder.currentMonth.setLayoutDirection(LAYOUT_DIRECTION_LTR);
                binder.prevMonth.setLayoutDirection(LAYOUT_DIRECTION_LTR);
            }
        }

        this.hideCurrentMonth = typedArray.getBoolean(R.styleable.EsCalendarView_hideCurrentMonth, false);
        this.hidePreviousMonth = typedArray.getBoolean(R.styleable.EsCalendarView_hidePreviousMonth, false);
        if (binder != null) {
            binder.prevMonth.setVisibility(hidePreviousMonth ? GONE : VISIBLE);
            binder.currentMonth.setVisibility(hideCurrentMonth ? GONE : VISIBLE);
        }
//        Log.e(TAG, "include today : " + includeToday);
        this.daysCount = (int) typedArray.getInt(R.styleable.EsCalendarView_daysCount, daysCount);
//        Log.e(TAG, " daysCount : " + daysCount);
        previousMonthTextColor = typedArray.getColor(R.styleable.EsCalendarView_previousMonthTextColor, getResources().getColor(R.color.black));
        if (binder != null)
            binder.prevMonth.setTextColor(previousMonthTextColor);

        previousMonthTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_previousMonthTextSize, previousMonthTextSize);
        if (binder != null)
            binder.prevMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, previousMonthTextSize);

        currentMonthTextColor = typedArray.getColor(R.styleable.EsCalendarView_currentMonthTextColor, getResources().getColor(R.color.md_blue_600));
        if (binder != null)
            binder.currentMonth.setTextColor(currentMonthTextColor);

        currentMonthTextSize = (int) typedArray.getDimension(R.styleable.EsCalendarView_currentMonthTextSize, currentMonthTextSize);
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

    private boolean isValid(String splitter) {
        return splitter != null;
    }


    private DateModel selectedModel;
    private Integer selectedPosition;

    private void initializeCalendar() {

        binder.datesRecycler.setHasFixedSize(true);
        binder.datesRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false));
        OverScrollDecoratorHelper.setUpOverScroll(binder.datesRecycler, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        adapter = new DatesAdapter();
        binder.datesRecycler.setAdapter(adapter);


        adapter.setOnDateSelectedListener((model, position) -> {
            selectedModel = model;
            selectedPosition = position;
            binder.prevMonth.setText(model.getPrevMonth());
            binder.currentMonth.setText(model.getCurrentMonth());
            if (esCalendarListener != null)
                esCalendarListener.onDateSelectedListener(model, position);
        });

        /*Listen for scrolling to get the current and previous data depending on the showing items*/
        RecyclerViewPositionHelper helper = new RecyclerViewPositionHelper(binder.datesRecycler);
        ViewTreeObserver observer = binder.datesRecycler.getViewTreeObserver();
        observer.addOnScrollChangedListener(() -> {
            if (binder == null)
                return;
            if (datesList != null)
                if (!datesList.isEmpty()) {
                    /*if the selected date is visible, then update views according to it */
                    if (getVisiblePositions(helper).contains(adapter.getSelectedPosition())) {
                        binder.currentMonth.setText(datesList.get(adapter.getSelectedPosition()).getCurrentMonth());
                        binder.prevMonth.setText(datesList.get(adapter.getSelectedPosition()).getPrevMonth());
                    } else {
                        /* the selected date is NOT visible, so we update views according to first item visible */
                        if (helper.findFirstVisibleItemPosition() < 0)
                            return;
                        binder.currentMonth.setText(datesList.get(helper.findFirstVisibleItemPosition()).getCurrentMonth());
                        binder.prevMonth.setText(datesList.get(helper.findFirstVisibleItemPosition()).getPrevMonth());
                    }
                }
        });

    }


    private void bind() {
        binder = CalendarViewBinding.inflate((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE), this, true);
        esAttrs = new EsAttrs();
    }

    @TestOnly
    private void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount == null ? 120 : daysCount;
        adapter.reset();
        populateDates(daysCount);
        postInvalidate();
    }


    private PrimeCalendar getNewCalendarInstance() {
        return arabicSupport
               ? CalendarFactory.newInstance(CalendarType.CIVIL, Utils.getLocal(getContext()))
               : CalendarFactory.newInstance(CalendarType.CIVIL, Locale.ENGLISH);
    }

    /**
     * ''' for not overriding the new times in the old calendars '''
     *
     * @param newTime the time of the calendar in the for loop
     * @return a new PrimeCalendar instance with the new time
     */
    private PrimeCalendar getNewCalendarInstanceWithThisTime(Date newTime) {
        PrimeCalendar instance = getNewCalendarInstance();
        instance.setTime(newTime);
        return instance;
    }


    private String getPrevMonth(PrimeCalendar todayCalendar) {
        PrimeCalendar prevCalendar = getNewCalendarInstance();
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


    /*
     * return the selected model
     * */
    public DateModel getSelectedCalendar() {
        return selectedModel == null ? datesList == null ? new DateModel().getDummyMe()
                                                         : datesList.get(0) : selectedModel;
    }

    /*
     * returns the selected position in the @param datesList
     */
    public Integer getSelectedPosition() {
        return selectedPosition == null ? 0 : selectedPosition;
    }

    /*
     * returns the wanted dates models, or an empty array
     * */
    public ArrayList<DateModel> getDatesList() {
        return datesList == null ? new ArrayList<>() : datesList;
    }

    private ArrayList<DateModel> getDates(Integer dateCounts) {
        PrimeCalendar todayCalendar = getNewCalendarInstance();
        Date date = new Date();
        todayCalendar.setTime(date);


        for (Integer count = 0; count < dateCounts; count++) {

            // include today in the calendar
            if (includeToday) {
                if (count > 0) // skipp first day from incrementing to include it
                    todayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            } else {
                todayCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            todayCalendar.add(Calendar.MONTH, 0);
            todayCalendar.add(Calendar.YEAR, 0);


//            Timber.e(todayCalendar.getShortDateString());
//            Log.e(TAG, "include today : " + includeToday);
//            Log.e(TAG, "getDates: " + todayCalendar.getShortDateString());


            datesList.add(
                    getDateModel(todayCalendar, count)
            );

        }

        return datesList;

    }

    @NotNull
    private DateModel getDateModel(PrimeCalendar todayCalendar, Integer count) {

        return new DateModel(
                count, /* a unique id for the recycler's getItemId*/
                getNewCalendarInstanceWithThisTime(todayCalendar.getTime()),
                todayCalendar.getWeekDayNameShort(),
                getDayWithZero(todayCalendar),
                getCurrentMonth(todayCalendar),
                getPrevMonth(todayCalendar),
                count == 0   /* Means if today then select it */,

                /* if arabic supported
                 then change apiDate to english chars
                 for the sake of those Backend Developers whom can understand nothing but FUCKING ENGLISH!*/
                getApiDate(todayCalendar)
        );
    }

    @NotNull
    private String getApiDate(PrimeCalendar todayCalendar) {
        return arabicSupport
               ? Utils.digitsFromARToEN(todayCalendar.getShortDateString()).replace("/", dateSplitChar)
               : todayCalendar.getShortDateString().replace("/", dateSplitChar);
    }

}
