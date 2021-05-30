package com.example.cinemabooking.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemabooking.Listeners.CinemaOnclicklistener;
import com.example.cinemabooking.Model.Cinema;
import com.example.cinemabooking.R;

import java.util.ArrayList;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Cinema> mImagenames = new ArrayList<>();
CinemaOnclicklistener cinemaOnclicklistener;
    private Context mContext;

    public CinemaAdapter(Context mContext, ArrayList<Cinema> mImagenames,CinemaOnclicklistener cinemaOnclicklistener) {
        this.mImagenames = mImagenames;
        this.mContext = mContext;
        this.cinemaOnclicklistener = cinemaOnclicklistener;
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
        Glide.with(mContext).asBitmap().load(mImagenames.get(position).getImage()).into(holder.image);
        holder.imagename.setText(mImagenames.get(position).getName());
        holder.address.setText(mImagenames.get(position).getAddress());

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
            parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cinema cinema =mImagenames.get(getAdapterPosition());
                    cinemaOnclicklistener.cinemaOnClickListener(cinema);
                }
            });
        }
    }
}