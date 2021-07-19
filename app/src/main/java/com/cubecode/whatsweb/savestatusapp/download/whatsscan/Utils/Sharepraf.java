package com.cubecode.whatsweb.savestatusapp.download.whatsscan.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharepraf {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("quotes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    public static void putLong(String key, long value)  {
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(String key, long value) {
        return sharedPreferences.getLong(key, value);
    }

    public static void putFloat(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(String key, Float value) {
        return sharedPreferences.getFloat(key, value);
    }

}
