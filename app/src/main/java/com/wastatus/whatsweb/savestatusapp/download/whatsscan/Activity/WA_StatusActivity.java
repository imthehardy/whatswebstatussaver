package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Fragment.ImageStatusFragment;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Fragment.VideoStatusFragment;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

public class WA_StatusActivity extends AppCompatActivity {

    TextView Wa_title;
    TabLayout status_tab;
    ViewPager status_pager;
    boolean Wa_status = false;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wastatus);

        back = findViewById(R.id.back_icon);
        Wa_title = findViewById(R.id.Wa_title);
        status_tab = findViewById(R.id.status_tab);
        status_pager = findViewById(R.id.status_pager);
        Wa_status = getIntent().getBooleanExtra("wa_status", false);

        LinearLayout banner_100 = findViewById(R.id.banner_50);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.GOOGLE_AD_BANNER_ID));
        AdRequest adRequest = new AdRequest.Builder().build();
        banner_100.addView(adView);
        adView.loadAd(adRequest);

        if (Wa_status) {
            Wa_title.setText(R.string.wastatus);
        } else {
            Wa_title.setText(R.string.saved_status);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        status_pager.setAdapter(viewPagerAdapter);
        status_tab.setupWithViewPager(status_pager);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new ImageStatusFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("Wa_status", Wa_status);
                fragment.setArguments(bundle);
            } else if (position == 1) {
                fragment = new VideoStatusFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("Wa_status", Wa_status);
                fragment.setArguments(bundle);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Image Status";
            } else if (position == 1) {
                title = "Video Status";
            }
            return title;
        }
    }
}