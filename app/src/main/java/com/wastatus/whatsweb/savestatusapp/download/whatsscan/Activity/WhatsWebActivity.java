 package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class WhatsWebActivity extends AppCompatActivity {

    WebView whats_web;
    ProgressBar progress_seek;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsweb);

        whats_web = findViewById(R.id.whats_web);
        progress_seek = findViewById(R.id.progress_seek);
        back = findViewById(R.id.back_icon);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayout banner_100 = findViewById(R.id.banner_50);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.GOOGLE_AD_BANNER_ID));
        AdRequest adRequest = new AdRequest.Builder().build();
        banner_100.addView(adView);
        adView.loadAd(adRequest);

        whats_web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress == 100)
                    progress_seek.setVisibility(View.GONE);
            }
        });
        whats_web.loadUrl("https://web.whatsapp.com/");
        whats_web.setWebViewClient(new WebViewClient());
        whats_web.getSettings().setJavaScriptEnabled(true);
        whats_web.getSettings().setUseWideViewPort(true);
        whats_web.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Win64; x64; rv:46.0) Gecko/20100101 Firefox/68.0");
        whats_web.getSettings().setGeolocationEnabled(true);
        whats_web.getSettings().setDomStorageEnabled(true);
        whats_web.getSettings().setDatabaseEnabled(true);
        whats_web.getSettings().setSupportMultipleWindows(true);
        whats_web.getSettings().setAppCacheEnabled(true);
        whats_web.getSettings().setNeedInitialFocus(true);
        whats_web.getSettings().setLoadWithOverviewMode(true);
        whats_web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        whats_web.getSettings().setBlockNetworkLoads(true);
        whats_web.getSettings().setBlockNetworkImage(true);
        whats_web.getSettings().setBuiltInZoomControls(true);
        whats_web.setInitialScale(100);


    }
}