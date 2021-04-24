package com.example.filmesdadecada;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.filmesdadecada.DAO.MovieDAO;
import com.example.filmesdadecada.Entities.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView moviesListView;
    private ArrayAdapter adapter;
    private List<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatActionButton = findViewById(R.id.fab);
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MovieForm.class);
                intent.putExtra("action", "new");
                startActivity(intent);
            }

        });

        moviesListView = findViewById(R.id.listViewMovies);
        getMoviesList();
        listViwFactory();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMoviesList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMoviesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listViwFactory(){
        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = moviesList.get(position);
                Intent intent = new Intent(MainActivity.this, MovieForm.class);
                intent.putExtra("action", "edit");
                intent.putExtra("id", selectedMovie.getId());
                startActivity(intent);
            }

        });

        moviesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = moviesList.get(position);
                deleteMovie(selectedMovie);
                return true;
            }
        });
    }

    private void getMoviesList(){
        moviesList = MovieDAO.getMovies(this);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, moviesList);
        moviesListView.setAdapter(adapter);
    }

    private void deleteMovie(Movie movie){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setIcon(android.R.drawable.ic_input_delete);
        alert.setTitle(R.string.attention_text);
        alert.setMessage(R.string.movie_exclude_text);
        alert.setNeutralButton(R.string.cancel, null);

        alert.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MovieDAO.deleteMovie(MainActivity.this, movie.getId());
                getMoviesList();
            }
        });

        alert.show();
    }
}