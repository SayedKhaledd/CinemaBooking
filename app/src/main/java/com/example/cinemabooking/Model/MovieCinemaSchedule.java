package com.example.cinemabooking.Model;


import java.io.Serializable;
import java.util.Date;

public class MovieCinemaSchedule implements Serializable {
    Film movie;
    Cinema Cinema;
    Date mdate;
    int numOfEmptySeats;
    int price;

    public MovieCinemaSchedule(Film movie, com.example.cinemabooking.Model.Cinema cinema,
                               Date date, int numOfEmptySeats, int price) {
        this.movie = movie;
        Cinema = cinema;
        this.mdate = date;
        this.price = price;
        this.numOfEmptySeats = numOfEmptySeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Film getMovie() {
        return movie;
    }

    public void setMovie(Film movie) {
        this.movie = movie;
    }

    public com.example.cinemabooking.Model.Cinema getCinema() {
        return Cinema;
    }

    public void setCinema(com.example.cinemabooking.Model.Cinema cinema) {
        Cinema = cinema;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public int getNumOfEmptySeats() {
        return numOfEmptySeats;
    }

    public void setNumOfEmptySeats(int numOfEmptySeats) {
        this.numOfEmptySeats = numOfEmptySeats;
    }
}
