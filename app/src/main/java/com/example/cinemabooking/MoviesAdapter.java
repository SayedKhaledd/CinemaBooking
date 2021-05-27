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
import com.example.cinemabooking.Model.Film;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Film> mImagenames = new ArrayList<>();
    private Context mContext;
    FilmOnClickListener filmOnClickListener;

    public MoviesAdapter(Context mContext, ArrayList<Film> mImagenames,FilmOnClickListener filmOnClickListener) {
        this.mImagenames = mImagenames;
        this.mContext = mContext;
        this.filmOnClickListener=filmOnClickListener;    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        Glide.with(mContext).asBitmap().load(mImagenames.get(position).getImageUML()).into(holder.image);
        holder.imagename.setText(mImagenames.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mImagenames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView imagename;
        RelativeLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imagename = itemView.findViewById(R.id.image_name);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Film film =mImagenames.get(getAdapterPosition());
                    filmOnClickListener.filmAddOnClickListener(film);
                }
            });        }
    }
}
