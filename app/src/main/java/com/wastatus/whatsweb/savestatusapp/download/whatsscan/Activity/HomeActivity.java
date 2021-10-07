package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Activity;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.BuildConfig;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Utils.AppCons;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Utils.Sharepraf;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    CardView cv_whats_web, cv_wa_status, cv_saved_status, cv_whats_direct_msg, cv_whats_share;
    ImageView whatsapp_open, menu_open;
    DrawerLayout drawer;
    String TAG = "HomeActivity";
    InterstitialAd mInterstitialAd;
    int ads = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadNativeAd();

        drawer = findViewById(R.id.drawer_ly);
        menu_open = findViewById(R.id.menu_open);
        cv_saved_status = findViewById(R.id.cv_saved_status);
        cv_wa_status = findViewById(R.id.cv_wa_status);
        cv_whats_web = findViewById(R.id.cv_whats_web);
        cv_whats_direct_msg = findViewById(R.id.cv_whats_direct_msg);
        cv_whats_share = findViewById(R.id.cv_whats_share);
        whatsapp_open = findViewById(R.id.whatsapp_open);

        if (AppCons.checkSelfPermission(this)) {
            AppCons.requestPermission(this);
        } else {
            Init();
        }

        whatsapp_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    startActivity(launchIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        menu_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigation_ly);
        View headerLayout = navigationView.inflateHeaderView(R.layout.navigation_ly);
        RelativeLayout rv_whatsapp_option = headerLayout.findViewById(R.id.rv_whatsapp_option);
        RelativeLayout rv_privacy_policy = headerLayout.findViewById(R.id.rv_privacy_policy);
        RelativeLayout rv_rateus = headerLayout.findViewById(R.id.rv_rateus);
        TextView version_code = headerLayout.findViewById(R.id.version_code);
        TextView wa_or_wab = headerLayout.findViewById(R.id.wa_or_wab);

        if (Sharepraf.getBoolean(AppCons.WA_OPTIONS, true)) {
            wa_or_wab.setText(getResources().getString(R.string.whatsapp));
        } else {
            wa_or_wab.setText(getResources().getString(R.string.wa_b));
        }

        version_code.setText(BuildConfig.VERSION_NAME);

        rv_whatsapp_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.whatsapp_options);
                dialog.getWindow().setLayout((int) (AppCons.getWidth(HomeActivity.this) * 0.85f), ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                RelativeLayout whatsapp = dialog.findViewById(R.id.whatsapp);
                RelativeLayout wa_business = dialog.findViewById(R.id.wa_business);
                ImageView check_whatsapp = dialog.findViewById(R.id.check_whatsapp);
                ImageView check_wa_business = dialog.findViewById(R.id.check_wa_business);

                if (Sharepraf.getBoolean(AppCons.WA_OPTIONS, true)) {
                    check_whatsapp.setVisibility(View.VISIBLE);
                    check_wa_business.setVisibility(View.GONE);
                } else {
                    check_whatsapp.setVisibility(View.GONE);
                    check_wa_business.setVisibility(View.VISIBLE);
                }

                whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Sharepraf.putBoolean(AppCons.WA_OPTIONS, true);
                        wa_or_wab.setText(getResources().getString(R.string.whatsapp));
                        dialog.dismiss();
                        drawer.closeDrawers();
                    }
                });

                wa_business.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Sharepraf.putBoolean(AppCons.WA_OPTIONS, false);
                        wa_or_wab.setText(getResources().getString(R.string.wa_b));
                        dialog.dismiss();
                        drawer.closeDrawers();
                    }
                });

                dialog.show();
            }
        });

        rv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                startActivity(new Intent(getApplicationContext(), WebViewActivity.class));
            }
        });

        rv_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                startActivity(intent);
            }
        });
    }

    public void Init() {
        cv_saved_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads = 1;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(HomeActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });

        cv_wa_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads = 2;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(HomeActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });

        cv_whats_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads = 3;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(HomeActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });

        cv_whats_direct_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads = 4;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(HomeActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });

        cv_whats_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ads = 5;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(HomeActivity.this);
                } else {
                    PerformOnclick();
                }
            }
        });
    }

    public void PerformOnclick() {
        Intent intent;
        switch (ads) {
            case 1:
                intent = new Intent(HomeActivity.this, WA_StatusActivity.class);
                intent.putExtra("wa_status", false);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(HomeActivity.this, WA_StatusActivity.class);
                intent.putExtra("wa_status", true);
                startActivity(intent);
                break;
            case 3:
                startActivity(new Intent(HomeActivity.this, WhatsWebActivity.class));
                break;
            case 4:
                startActivity(new Intent(HomeActivity.this, Direct_MsgActivity.class));
                break;
            case 5:
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                String sb = "Save WhatsApp status app From Play Store\n\n" + "https://play.google.com/store/apps/details?id=" + getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, sb);
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppCons.PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean readStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (readStorageAccepted && writeStorageAccepted) {
                    Init();
                } else {
                    AppCons.requestPermission(this);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawers();
        } else {
            exitDialog();
        }
    }

    public void exitDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.getWindow().setLayout((int) (AppCons.getWidth(this) * 0.85f), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button exit = (Button) dialog.findViewById(R.id.btnexit);
        Button cancel = (Button) dialog.findViewById(R.id.btncancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
                System.exit(0);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

    private NativeAd nativeAd;

    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);

        VideoController vc = nativeAd.getMediaContent().getVideoController();

        if (vc.hasVideoContent()) {

            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }

    private void loadNativeAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.GOOGLE_AD_NATIVE_ID));

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = isDestroyed();
                        }
                        if (isDestroyed || isFinishing() || isChangingConfigurations()) {
                            nativeAd.destroy();
                            return;
                        }
                        if (HomeActivity.this.nativeAd != null) {
                            HomeActivity.this.nativeAd.destroy();
                        }
                        HomeActivity.this.nativeAd = nativeAd;
                        FrameLayout frameLayout = findViewById(R.id.native_ad_container);
                        NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(false).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                                        String error =
                                                String.format(
                                                        "domain: %s, code: %d, message: %s",
                                                        loadAdError.getDomain(),
                                                        loadAdError.getCode(),
                                                        loadAdError.getMessage());
                                        Toast.makeText(
                                                HomeActivity.this,
                                                "Failed to load native ad with error " + error,
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

}