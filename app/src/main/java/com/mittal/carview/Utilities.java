package com.mittal.carview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mittal on 4/7/16.
 */
public class Utilities  {
    private ConnectivityManager cm;
    static Context context;
    private static Utilities singleton = null;
    public static String outPut="dd MMM, yyyy";
    public static String inputNew="yyyy/MM/dd";
    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Utilities() {
    }

    /* Static 'instance' method */
    public static Utilities getInstance(Context mContext) {
        context = mContext;
        if (singleton == null)
            singleton = new Utilities();
        return singleton;
    }

    /**
     * Method for getting application version code
     */
    public String getAppVersion() {
        String appVersion = "";
        try {
            appVersion = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }


    public static void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) context).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String formateDateShownew(String inputDate) {
        String date="";
        SimpleDateFormat format = new SimpleDateFormat(inputNew);
        try {
            Date newDate = format.parse(inputDate);

            format = new SimpleDateFormat(outPut);
            date = format.format(newDate);

        } catch (Exception e) {

        }
        return date;
    }


}

