package com.example.cinemabooking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImagenames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private Context mContext;

    public CinemaAdapter(Context mContext, ArrayList<String> mImagenames, ArrayList<String> mImages, ArrayList<String> mAddress) {
        this.mImagenames = mImagenames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mAddress = mAddress;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);
        holder.imagename.setText(mImagenames.get(position));
        holder.address.setText(mAddress.get(position));
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on:"+ mImagenames.get(position));
                Toast.makeText(mContext, mImagenames.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImagenames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView imagename;
        TextView address;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imagename = itemView.findViewById(R.id.image_name);
            address = itemView.findViewById(R.id.address);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }
}