package com.esmaeel.calendarlibrary;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Locale;

public class Utils {
    public static ArrayList<Integer> getArrayOfSize(Integer size) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i = 0;
        while (i <= size) {
            arrayList.add(i);
            i++;
        }
        return arrayList;
    }

    public static Locale getLocal(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }


    public static String digitsFromARToEN(String value) {
        if (value == null || value.isEmpty())
            return "";
        return value.replace("٠", "0")
                .replace("١", "1")
                .replace("٢", "2")
                .replace("٣", "3")
                .replace("٤", "4")
                .replace("٥", "5")
                .replace("٦", "6")
                .replace("٧", "7")
                .replace("٨", "8")
                .replace("٩", "9");
    }
}
