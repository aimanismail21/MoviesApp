package com.aimanismail.moviesapp;

import java.util.ArrayList;

public class Movie {
    private String mName;
    private String mDescription;
    private String mMovieLink;

    public Movie() {
    }

    public Movie(String title, String description, String movieLink) {
        mName = title;
        mDescription = description;
        mMovieLink = movieLink;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmMovieLink() {
        return mMovieLink;
    }
    // Tracks the number of movies added
    private static int lastMovieID = 0;

    public static ArrayList<Movie> createMovieList(int numContacts) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 1; i <= numContacts; i++) {
            movies.add(new Movie("Movie: " + ++lastMovieID, "Description:", "IMDB Link:"));
        }

        return movies;
    }
}
