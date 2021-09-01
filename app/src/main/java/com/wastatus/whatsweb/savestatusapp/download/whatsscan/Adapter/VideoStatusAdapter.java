package com.wastatus.whatsweb.savestatusapp.download.whatsscan.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Model.ImageStatusModel;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Model.VideoStatusModel;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.R;
import com.wastatus.whatsweb.savestatusapp.download.whatsscan.Utils.AppCons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class VideoStatusAdapter extends RecyclerView.Adapter<VideoStatusAdapter.MyClass> {

    Activity context;
    ArrayList<VideoStatusModel> videoStatusModels;
    boolean wa_status;

    public VideoStatusAdapter(Activity context, ArrayList<VideoStatusModel> videoStatusModels, boolean wa_status) {
        this.context = context;
        this.videoStatusModels = videoStatusModels;
        this.wa_status = wa_status;
    }

    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.videostatusadapter, parent, false);
        return new MyClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClass holder, int position) {

        Glide.with(context).load(videoStatusModels.get(position).getPath()).into(holder.video_status);

        if (wa_status){
            holder.vid_status_save.setVisibility(View.VISIBLE);
        } else {
            holder.vid_status_save.setVisibility(View.GONE);
        }

        holder.vid_status_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(AppCons.SAVED_PATH);
                if (!file.exists()){
                    file.mkdir();
                }
                final String path = videoStatusModels.get(position).getPath();
                String filename=path.substring(path.lastIndexOf("/")+1);
                File newFile = new File(file +"/"+ filename);
                File oldFile = new File(videoStatusModels.get(position).getPath());

                try {
                    InputStream fis = new FileInputStream(oldFile);
                    OutputStream fos = new FileOutputStream(newFile);

                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = fis.read(buf)) > 0) {
                        fos.write(buf, 0, len);
                    }
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
            }
        });

        holder.vid_status_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("video/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(videoStatusModels.get(position).getPath()));
                context.startActivity(Intent.createChooser(shareIntent, "Share Video"));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.status_view_video);
                dialog.getWindow().setLayout((int) (AppCons.getWidth(context) * 0.85f), ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                VideoView status_vid = dialog.findViewById(R.id.status_vid);
                Button share = (Button) dialog.findViewById(R.id.share);
                Button save = (Button) dialog.findViewById(R.id.save);

                status_vid.setVideoPath(videoStatusModels.get(position).getPath());
                status_vid.start();

                if (wa_status){
                    save.setVisibility(View.VISIBLE);
                } else {
                    save.setVisibility(View.GONE);
                }

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(videoStatusModels.get(position).getPath()));
                        context.startActivity(Intent.createChooser(shareIntent, "Share video"));
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File file = new File(AppCons.SAVED_PATH);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        final String path = videoStatusModels.get(position).getPath();
                        String filename = path.substring(path.lastIndexOf("/") + 1);

                        File newFile = new File(file +"/"+ filename);
                        File oldFile = new File(videoStatusModels.get(position).getPath());

                        try {
                            InputStream fis = new FileInputStream(oldFile);
                            OutputStream fos = new FileOutputStream(newFile);

                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = fis.read(buf)) > 0) {
                                fos.write(buf, 0, len);
                            }
                            fis.close();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoStatusModels.size();
    }

    public class MyClass extends RecyclerView.ViewHolder {

        ImageView video_status, vid_status_save, vid_status_share;

        public MyClass(@NonNull View itemView) {
            super(itemView);

            video_status = itemView.findViewById(R.id.image_status);
            vid_status_save = itemView.findViewById(R.id.img_status_save);
            vid_status_share = itemView.findViewById(R.id.img_status_share);

        }
    }
}
