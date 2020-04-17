package com.aimanismail.moviesapp;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button movieLinkButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.movie_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.movie_description);
            movieLinkButton = (Button) itemView.findViewById(R.id.movie_link_button);
        }
    }
    // Store a member variable for the movies
    private List<Movie> mMovies;

    // Pass in the movies array into the constructor
    public MoviesAdapter(List<Movie> movies) {
        mMovies = movies;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.movie_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Movie movie = mMovies.get(position);
        // Set item views based on your views and data model
        TextView title = viewHolder.titleTextView;
        title.setText(movie.getmName());
        TextView description = viewHolder.descriptionTextView;
        description.setText(movie.getmDescription());
        Button button = viewHolder.movieLinkButton;
        button.setText(movie.getmMovieLink());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}