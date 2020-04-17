package com.aimanismail.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<Movie> movies;
    Button addMovie;
    private int movieId;
    static private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseFirestore.getInstance();
        CollectionReference databaseMovies = database.collection("movies");

        // Test write data to Firebase Firestore - working.
//        Movie testMovie = new Movie("Fight Club", "Satire", "imdb.com");
//        databaseMovies.add(testMovie);

        // Lookup the recyclerview in activity layout
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = Movie.createMovieList(0);
        final MoviesAdapter adapter = new MoviesAdapter(movies);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        // Assign method to button
        addMovie = findViewById(R.id.add_movie_FAB);
        // When + Button is clicked a DialogFragment is generated asking the user for input.
        addMovie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddMovieDialogFragment movieDialog = new AddMovieDialogFragment(movies, adapter, movieId);
                movieDialog.show(getSupportFragmentManager(), "Movie Information");
            }
        });
    }

    public static class AddMovieDialogFragment extends DialogFragment {
        ArrayList<Movie> mMovies;
        MoviesAdapter mAdapter;
        int MovieIndex;  //Updates as movies are added

        public AddMovieDialogFragment(ArrayList<Movie> movies, MoviesAdapter adapter, int index) {
            mMovies = movies;
            mAdapter = adapter;
            MovieIndex = index;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            // EditText for Title
            final EditText title = new EditText(getActivity());
//            title.setId(R.id.dialog_title);
            title.setHint("Movie Title");
            layout.addView(title);
            // EditText for Description
            final EditText description = new EditText(getActivity());
//            description.setId(R.id.dialog_description);
            description.setHint("Description of the Movie");
            layout.addView(description);
            // EditText for Link
            final EditText link = new EditText(getActivity());
//            link.setId(R.id.dialog_link);
            link.setHint("IMDB Link to Movie");
            layout.addView(link);
            dialog.setView(layout);

            // The Save and Cancel Behaviours
            dialog.setMessage(R.string.dialog_prompt)
                    .setPositiveButton(R.string.dialog_submit, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Retrieves the entered information
                            String movie_title = title.getText().toString().trim();
                            String movie_description = description.getText().toString().trim();
                            String movie_link = link.getText().toString().trim();
                            // Input converted into Movie
                            Movie newMovie = new Movie(movie_title, movie_description, movie_link);
                            CollectionReference databaseMovies = database.collection("movies");

                            databaseMovies.add(newMovie);
                            // Adds movie to list of movies that populate RecyclerView.
                            mMovies.add(MovieIndex, newMovie);
                            // Notify the adapter that a new movie was inserted at index
                            mAdapter.notifyItemInserted(MovieIndex);
                            // Notify Firebase with a Write task.
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return dialog.create();
        }

    }


}
