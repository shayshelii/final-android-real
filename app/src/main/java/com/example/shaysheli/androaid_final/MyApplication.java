package com.example.shaysheli.androaid_final;

import android.app.Application;
import android.content.Context;

/**
 * Created by ShaySheli on 31/07/2017.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getMyContext(){
        return context;
    }
}
