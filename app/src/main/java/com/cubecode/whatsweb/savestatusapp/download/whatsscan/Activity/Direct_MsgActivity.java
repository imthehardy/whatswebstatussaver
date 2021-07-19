package com.cubecode.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubecode.whatsweb.savestatusapp.download.whatsscan.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.hbb20.CountryCodePicker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Direct_MsgActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText phoneText, whats_dir_msg;
    String phoneNumber;
    TextView msg_send;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directmsg);

        ccp = findViewById(R.id.ccp);
        phoneText = findViewById(R.id.phoneText);
        msg_send = findViewById(R.id.msg_send);
        whats_dir_msg = findViewById(R.id.whats_dir_msg);
        back = findViewById(R.id.back_icon);

        LinearLayout banner_100 = findViewById(R.id.banner_50);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(getString(R.string.GOOGLE_AD_BANNER_ID));
        AdRequest adRequest = new AdRequest.Builder().build();
        banner_100.addView(adView);
        adView.loadAd(adRequest);

        ccp.registerCarrierNumberEditText(phoneText);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = ccp.getFullNumberWithPlus();
                if (phoneText.getText().toString().length() == 10) {
                    if (whats_dir_msg.getText().toString().length() > 0) {
                        whats_dir_msg.getText().toString();
                        Intent intent = null;
                        try {
                            intent = new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=" + URLEncoder.encode(whats_dir_msg.getText().toString(), "UTF-8") + "&phone=" + phoneNumber));
                            try {
                                startActivity(intent);
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(Direct_MsgActivity.this, "WhatsApp have not been installed.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    } else {
                        whats_dir_msg.setError("Please enter massage");
                    }
                } else {
                    phoneText.setError("Please enter correct number");
                }
            }
        });

    }
}