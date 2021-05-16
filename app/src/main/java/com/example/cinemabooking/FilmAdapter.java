package com.example.cinemabooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemabooking.Model.Film;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {
    List<Film> films;
    Context context;

    public FilmAdapter(List<Film> films, Context context) {
        this.films = films;
        this.context = context;

    }

    @NonNull
    @Override
    public FilmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.MyViewHolder holder, int position) {
        Film film = films.get(position);
        holder.name.setText(film.getName());

        holder.description.setText(film.getDescription());
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name, description;
        Button add, rate;
        ImageView imageView;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            name = itemView.findViewById(R.id.film_name);
            description = itemView.findViewById(R.id.des);

            add = itemView.findViewById(R.id.add);
            imageView = itemView.findViewById(R.id.image);


        }


    }
}
