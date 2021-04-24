package com.example.filmesdadecada.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.filmesdadecada.DB.Connection;
import com.example.filmesdadecada.Entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private static final String WRITABLE_DB_TYPE = "writable";

    private static SQLiteDatabase getDatabase(Context context, String type){
        Connection connection = new Connection(context);

        SQLiteDatabase database;
        if(type.equals("writable")){
            database = connection.getWritableDatabase();
        } else {
            database = connection.getReadableDatabase();
        }
        return database;
    }

    public static void insertNewMovie(Context context, Movie movie){
        ContentValues values = new ContentValues();
        values.put("name", movie.getName());
        values.put("year", movie.getYear());

        SQLiteDatabase database = getDatabase(context, WRITABLE_DB_TYPE);

        database.insert("movie", null, values);
    }

    public static void editMovie(Context context, Movie movie){
        ContentValues values = new ContentValues();
        values.put("name", movie.getName());
        values.put("year", movie.getYear());

        Connection connection = new Connection(context);
        SQLiteDatabase database = connection.getWritableDatabase();

        database.update("movie", values, "id = " + movie.getId(), null);
    }

    public static void deleteMovie(Context context, int id){
        Connection connection = new Connection(context);
        SQLiteDatabase database = connection.getWritableDatabase();

        database.delete("movie", "id = "+ id, null);
    }

    public static List<Movie> getMovies(Context context){
        List<Movie> movies = new ArrayList<>();

        Connection connection = new Connection(context);

        SQLiteDatabase database = connection.getReadableDatabase();

        Cursor result = database.rawQuery("SELECT * FROM movie ORDER BY name", null );

        if( result.getCount() > 0 ){

            result.moveToFirst();

            do{

                Movie movie = new Movie(
                        result.getInt( 0)
                        ,result.getString(1)
                        ,( result.getInt(2) )
                );

                movies.add( movie );

            }while( result.moveToNext() );

        }

        return movies;
    }

    public static Movie getMovieById(Context context, int id){

        Connection connection = new Connection(context);

        SQLiteDatabase db = connection.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nome, ano FROM filme WHERE id = " + id, null );

        if( cursor.getCount() > 0 ){

            cursor.moveToFirst();

            Movie movie = new Movie(
                    cursor.getInt( 0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );

            return movie;

        }else{

            return null;

        }

    }
}
