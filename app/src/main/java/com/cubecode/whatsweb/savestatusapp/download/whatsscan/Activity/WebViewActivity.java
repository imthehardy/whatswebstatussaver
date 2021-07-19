package com.cubecode.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cubecode.whatsweb.savestatusapp.download.whatsscan.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);
        final ProgressBar bar = findViewById(R.id.progress);

        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress == 100)
                    bar.setVisibility(View.GONE);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl("https://sites.google.com/view/whats-status-saver/home");

    }
}