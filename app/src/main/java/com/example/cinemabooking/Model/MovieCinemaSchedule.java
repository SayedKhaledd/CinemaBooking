package com.example.cinemabooking.Model;


import java.io.Serializable;
import java.util.Date;

public class MovieCinemaSchedule implements Serializable {
    String date;
    int cinemaId, filmId;
    int numberofseats;
    int price;
    Cinema cinema;

    public Cinema getCinema() {
        return cinema;
    }

    public MovieCinemaSchedule() {
    }

    public MovieCinemaSchedule(String date, int cinemaId, int filmId, int numberofseats, int price, Cinema cinema) {
        this.date = date;
        this.cinemaId = cinemaId;
        this.filmId = filmId;
        this.numberofseats = numberofseats;
        this.price = price;
        this.cinema = cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getNumberofseats() {
        return numberofseats;
    }

    public void setNumberofseats(int numberofseats) {
        this.numberofseats = numberofseats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
