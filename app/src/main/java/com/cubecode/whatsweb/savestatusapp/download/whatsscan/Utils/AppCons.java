package com.cubecode.whatsweb.savestatusapp.download.whatsscan.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class AppCons {

    public static String WA_BA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";
    public static String WA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
    public static String WA_BA_PATH11 = Environment.getExternalStorageDirectory() + File.separator + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses";
    public static String WA_PATH11 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
    public static String SAVED_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Whats Web/";
    public static final int PERMISSION_CODE = 100;

    public static String WA_OPTIONS = "wa_options";

    public static boolean checkSelfPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
    }
    public static int getWidth(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getHeight(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
