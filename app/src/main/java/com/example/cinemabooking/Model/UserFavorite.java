package com.example.cinemabooking.Model;

public class UserFavorite {
    int movieId;
    String email;

    public UserFavorite() {
    }

    public UserFavorite(int movieId, String email) {
        this.movieId = movieId;
        this.email = email;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
