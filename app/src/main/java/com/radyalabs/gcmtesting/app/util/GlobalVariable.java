package com.radyalabs.gcmtesting.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aderifaldi on 27/05/2015.
 */
public class GlobalVariable {

    public static final String PREF_NAME = "TesGCMPref";
    public static final String ISREGISTERPUSH = "isRegisterPush";
    private static final String GCM_TOKEN = "deviceToken";
    private static final String DEVICE_ID = "deviceId";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_REQUEST_CODE = "requestCode";

    public static void saveIsRegisterPush(Context context, boolean data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ISREGISTERPUSH, data);
        editor.apply();
    }

    public static boolean getIsRegisterPush(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ISREGISTERPUSH, false);
    }

    public static void saveGCMToken(Context context, String deviceToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GCM_TOKEN, deviceToken);
        editor.apply();
    }

    public static String getGCMToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        String deviceToken = sharedPreferences.getString(GCM_TOKEN, null);

        return deviceToken;
    }

    public static void saveDeviceId(Context context, String deviceToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_ID, deviceToken);
        editor.apply();
    }

    public static String getDeviceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        String deviceToken = sharedPreferences.getString(DEVICE_ID, null);

        return deviceToken;
    }

    public static void saveNotificationId(Context context, int data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_ID, data);
        editor.apply();
    }

    public static int getNotificationId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt(NOTIFICATION_ID, 0);

        return data;
    }

    public static void saveRequestCode(Context context, int data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_REQUEST_CODE, data);
        editor.apply();
    }

    public static int getRequestCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariable.PREF_NAME, Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt(NOTIFICATION_REQUEST_CODE, 0);

        return data;
    }

}
