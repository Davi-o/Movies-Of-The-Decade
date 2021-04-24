package com.example.filmesdadecada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filmesdadecada.DAO.MovieDAO;
import com.example.filmesdadecada.Entities.Movie;

public class MovieForm extends AppCompatActivity {

    private EditText nameText;
    private Spinner yearSelection;
    private Button saveButton;
    private String action;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_form);

        nameText = findViewById(R.id.editTextMovieName);
        yearSelection = findViewById(R.id.movieYear);
        saveButton = findViewById(R.id.saveButton);

        action = getIntent().getStringExtra("action");

        if (action.equals("edit")) {
            loadForm();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMovie();
            }
        });
    }

    private void loadForm(){
        int movieId = getIntent().getIntExtra("movieId",0);
        if (movieId != 0) {
            movie = MovieDAO.getMovieById(this, movieId);
            nameText.setText(movie.getName());

            String[] years = getResources().getStringArray(R.array.years);

            for (int iterator = 0; iterator< years.length; iterator++) {
                if (Integer.parseInt(years[iterator]) == movie.getYear()) {
                    yearSelection.setSelection(iterator);
                }
            }
        }
    }

    private void saveMovie(){

        if (! (yearSelection.getSelectedItemPosition() == 0 || nameText.getText().toString().isEmpty()) ) {

            if (action.equals("edit")) {
                MovieDAO.editMovie(this,movie);
            } else {
                finish();
            }

            if (action.equals("new")) {
                movie = new Movie(
                        nameText.getText().toString(),
                        Integer.parseInt(yearSelection.getSelectedItem().toString())
                        );
                MovieDAO.insertNewMovie(this,movie);
            }else {
                nameText.setText("");
                yearSelection.setSelection(0);
            }

        } else {
            Toast.makeText(this, "Please, insert all the movie data.", Toast.LENGTH_SHORT).show();
        }

    }
}