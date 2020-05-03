package com.esmaeel.calendarlibrary;

import java.util.ArrayList;

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
}
