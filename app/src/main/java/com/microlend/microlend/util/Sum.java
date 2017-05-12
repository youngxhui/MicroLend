package com.microlend.microlend.util;

public class Sum {
    public static float getSum(float money, float reta, int mYear, int mMonth, int lYear, int lMonth) {
        int date = 0;
        if (mYear > lYear) {
            date = (mYear - lYear - 1) * 12 + 12 - lMonth + mMonth;
        } else {
            date = mMonth - lMonth;
        }
        float sum = (float) (money * reta * date * 0.001) + money;
        return sum;
    }
}
