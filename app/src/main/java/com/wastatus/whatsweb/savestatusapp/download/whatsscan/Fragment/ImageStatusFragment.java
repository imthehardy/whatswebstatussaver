package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Fragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Adapter.ImageStatusAdapter;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Model.ImageStatusModel;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Utils.AppCons;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Utils.Sharepraf;

import java.io.File;
import java.util.ArrayList;

public class ImageStatusFragment extends Fragment {

    RecyclerView rv_image_status;
    String TAG = "ImageStatusFragment";
    ArrayList<ImageStatusModel> imageStatusModels;
    ImageStatusAdapter imageStatusAdapter;
    ProgressBar progress;
    TextView no_status;
    boolean wa_status;
    public ImageStatusFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        wa_status = getArguments().getBoolean("Wa_status");
        View view = inflater.inflate(R.layout.fragment_imagestatus, container, false);

        rv_image_status = view.findViewById(R.id.rv_image_status);
        progress = view.findViewById(R.id.progress);
        no_status = view.findViewById(R.id.no_status);

        if (wa_status) {
            new GetData().execute();
        } else {
            new GetSavedData().execute();
        }

        return view;
    }

    public class GetSavedData extends AsyncTask<Void, Void , Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            imageStatusModels = new ArrayList<>();

            Log.e(TAG, "doInBackground: "+AppCons.SAVED_PATH );
            if (new File(AppCons.SAVED_PATH).exists()) {
                File file = new File(AppCons.SAVED_PATH);

                File[] status = file.listFiles();

                if (status != null && status.length > 0) {
                    for (File files : status){
                        if (files.getPath().endsWith(".jpg") || files.getPath().endsWith(".png")){
                            ImageStatusModel imageModel = new ImageStatusModel();
                            imageModel.setPath(files.getAbsolutePath());
                            imageStatusModels.add(imageModel);
                        }
                    }
                    Log.e(TAG, "doInBackground: done" );
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility(View.GONE);

            if (imageStatusModels.size() == 0) {
                no_status.setVisibility(View.VISIBLE);
            }

            rv_image_status.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            imageStatusAdapter = new ImageStatusAdapter(getActivity(), imageStatusModels, wa_status);
            rv_image_status.setAdapter(imageStatusAdapter);
        }

    }

    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            imageStatusModels = new ArrayList<>();

            File file;

            if (Sharepraf.getBoolean(AppCons.WA_OPTIONS, true)){
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    file = new File(AppCons.WA_PATH11);
                } else {
                    file = new File(AppCons.WA_PATH);
                }
            } else {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    file = new File(AppCons.WA_BA_PATH11);
                } else {
                    file = new File(AppCons.WA_BA_PATH);
                }
            }

            if (file.exists()) {

                File[] status = file.listFiles();

                if (status != null && status.length > 0) {
                    for (File files : status){
                            if (files.getPath().endsWith(".jpg") || files.getPath().endsWith(".png")){
                                ImageStatusModel imageModel = new ImageStatusModel();
                                imageModel.setPath(files.getAbsolutePath());
                                imageStatusModels.add(imageModel);
                            }
                    }

                }
                Log.e(TAG, "doInBackground: "+file.getPath() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility(View.GONE);

            if (imageStatusModels.size() == 0) {
                no_status.setVisibility(View.VISIBLE);
            }

            rv_image_status.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            imageStatusAdapter = new ImageStatusAdapter(getActivity(), imageStatusModels, wa_status);
            rv_image_status.setAdapter(imageStatusAdapter);

        }
    }

}