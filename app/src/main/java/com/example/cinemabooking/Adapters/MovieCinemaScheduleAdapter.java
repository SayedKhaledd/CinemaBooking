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
import com.example.cinemabooking.Listeners.MovieCinemaClickListenter;
import com.example.cinemabooking.Model.MovieCinemaSchedule;
import com.example.cinemabooking.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieCinemaScheduleAdapter extends RecyclerView.Adapter<MovieCinemaScheduleAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<MovieCinemaSchedule> movieCinema = new ArrayList<>();
    MovieCinemaClickListenter movieCinemaClickListenter;
    private Context mContext;

    public MovieCinemaScheduleAdapter(Context mContext, ArrayList<MovieCinemaSchedule> mImagenames, MovieCinemaClickListenter movieCinemaClickListenterinemaOnclicklistener) {
        this.movieCinema = mImagenames;
        this.mContext = mContext;
        this.movieCinemaClickListenter = movieCinemaClickListenterinemaOnclicklistener;
    }

    @Override
    public MovieCinemaScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cinema_schedule_item, parent, false);
        MovieCinemaScheduleAdapter.ViewHolder holder = new MovieCinemaScheduleAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCinemaScheduleAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext).asBitmap().load(movieCinema.get(position).getCinema().getImage()).into(holder.image);
        holder.imagename.setText(movieCinema.get(position).getCinema().getName());
        holder.address.setText(movieCinema.get(position).getCinema().getAddress());
        String strDate = movieCinema.get(position).getDate();
        holder.time.setText(strDate);
        holder.price.setText(movieCinema.get(position).getPrice() + "L.E");
    }

    @Override
    public int getItemCount() {
        return movieCinema.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imagename;
        TextView address, time, price;
        RelativeLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image1);
            imagename = itemView.findViewById(R.id.image_name1);
            address = itemView.findViewById(R.id.address1);
            time = itemView.findViewById(R.id.time1);
            price = itemView.findViewById(R.id.price1);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieCinemaSchedule movieCinemaSchedule = movieCinema.get(getAdapterPosition());
                    movieCinemaClickListenter.movieCinemaOnClickListener(movieCinemaSchedule);
                }
            });
        }
    }

}
