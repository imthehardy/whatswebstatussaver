package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashActivity extends AppCompatActivity {

    TextView start_btn;
    Animation animation, start_anim;
    InterstitialAd mInterstitialAd;
    String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        load_interAds();

        start_btn = findViewById(R.id.start_btn);
        start_anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        start_anim.setRepeatCount(1);

        animation = AnimationUtils.loadAnimation(this, R.anim.start_btn_anim);
        animation.setRepeatCount(Animation.INFINITE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start_btn.startAnimation(start_anim);
                start_btn.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        start_btn.startAnimation(animation);
                    }
                },1000);
            }
        }, 3000);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SplashActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });
    }

    public void PerformOnclick() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public void load_interAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getResources().getString(R.string.GOOGLE_AD_INTERSTITIAL_ID), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                PerformOnclick();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                PerformOnclick();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

}