package com.esmaeel.calendarlibrary;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ColorUtils;
import com.esmaeel.calendarlibrary.databinding.DateItemBinding;

import java.util.ArrayList;


public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.Holder> {

    static ArrayList<DateModel> datesList;
    EsCalendarView.EsAttrs esAttrs;

    public static OnDateSelectedInterface mOnDateSelectedInterface;


    public void setOnDateSelectedListener(OnDateSelectedInterface onDateSelectedInterface) {
        this.mOnDateSelectedInterface = onDateSelectedInterface;
    }

    public void reDraw(EsCalendarView.EsAttrs esAttrs) {
        this.esAttrs = esAttrs;
        notifyDataSetChanged();
    }

    public interface OnDateSelectedInterface {
        void onDateSelectedClickListener(DateModel model, int position);
    }

    public DatesAdapter() {
    }

    public void setDatesList(ArrayList<DateModel> datesList) {
        this.datesList = datesList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(DateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindViews(datesList.get(position), esAttrs);
    }

    @Override
    public int getItemCount() {
        return datesList == null ? 0 : datesList.size();
    }


    static
    class Holder extends RecyclerView.ViewHolder {
        DateItemBinding binder;
        private Integer lastSelectionPosition = 0;

        Holder(DateItemBinding binder) {
            super(binder.getRoot());
            this.binder = binder;
        }

        public void bindViews(DateModel dateModel, EsCalendarView.EsAttrs esAttrs) {

            /* updating the item size */
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binder.rootCard.getLayoutParams();
            layoutParams.height = esAttrs.getItemSize();
            layoutParams.width = esAttrs.getItemSize();
            binder.rootCard.setLayoutParams(layoutParams);
            binder.rootCard.setRadius(esAttrs.getRadius());
            binder.number.setText(dateModel.getNumber());
            binder.name.setText(dateModel.getName());

            binder.number.setTextSize(TypedValue.COMPLEX_UNIT_SP, esAttrs.getDayNumberTextSize());
            binder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, esAttrs.getDayNameTextSize());

            if (dateModel.isSelected()) {
                binder.number.setTextColor(esAttrs.getSelectedDayNumberTextColor());
                binder.name.setTextColor(esAttrs.getSelectedDayNameTextColor());

                binder.background.setBackground(ContextCompat.getDrawable(binder.getRoot().getContext(), R.drawable.date_circle_background_selected));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binder.background.getBackground().setColorFilter(Color.parseColor(ColorUtils.int2RgbString(esAttrs.getSelectedDayBackgroundColor())), PorterDuff.Mode.SRC_ATOP);
                }

            } else {
                binder.number.setTextColor(esAttrs.getNormalDayNumberTextColor());
                binder.name.setTextColor(esAttrs.getNormalDayNameTextColor());

                binder.background.setBackground(ContextCompat.getDrawable(binder.getRoot().getContext(), R.drawable.date_circle_background_selected));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    binder.background.getBackground().setColorFilter(Color.parseColor(ColorUtils.int2RgbString(esAttrs.getNormalDayBackgroundColor())), PorterDuff.Mode.SRC_ATOP);
                }
            }


            binder.getRoot().setOnClickListener(v -> {
                if (mOnDateSelectedInterface != null) {
                    mOnDateSelectedInterface.onDateSelectedClickListener(datesList.get(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }

                updateSelectedDate(getAbsoluteAdapterPosition());
            });
        }

        public void updateSelectedDate(int newPosition) {
            if (datesList.size() > newPosition) {
                for (int i = 0; i < datesList.size(); i++) {
                    if (datesList.get(i).getUniqeId().equals(datesList.get(newPosition).getUniqeId())) {
                        datesList.get(i).setSelected(true);
                        selectedDate = datesList.get(i).getApiDate();
                        selectedPosition = newPosition;
                    } else datesList.get(i).setSelected(false);
                }
                getBindingAdapter().notifyDataSetChanged();
            }
        }
    }

    public static String selectedDate = "";
    public static Integer selectedPosition = 0;

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    @Override
    public long getItemId(int position) {
        return datesList.get(position).getUniqeId();
    }

    @Override
    public int getItemViewType(int position) {
        return datesList.get(position).getUniqeId();
    }
}

