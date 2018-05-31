package com.example.dashan.introslider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dashan on 31/5/18.
 */

public class IntroSliderManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context mContext;

    public IntroSliderManager(Context context){
        this.mContext=context;
        sharedPreferences=context.getSharedPreferences("first",0);
        editor=sharedPreferences.edit();
    }
    public void setFirst(boolean isFirst){
        editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean Check(){
        return  sharedPreferences.getBoolean("check",true);
    }

}
