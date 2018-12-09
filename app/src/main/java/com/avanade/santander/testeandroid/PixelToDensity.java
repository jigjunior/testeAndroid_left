package com.avanade.santander.testeandroid;

import android.content.Context;
import android.util.TypedValue;

public class PixelToDensity {

    public static int convertToPixels(int dp, Context context){
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
