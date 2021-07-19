package com.cubecode.whatsweb.savestatusapp.download.whatsscan;

import android.app.Application;

import com.cubecode.whatsweb.savestatusapp.download.whatsscan.Utils.Sharepraf;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Sharepraf.init(getApplicationContext());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
}
