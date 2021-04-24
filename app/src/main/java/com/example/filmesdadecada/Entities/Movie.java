package com.example.filmesdadecada.Entities;

public class Movie {
    private int id;
    private String name;
    private int year;

    /**
     * @param id
     * @param name
     * @param year
     */
    public Movie(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    /**
     * @param name
     * @param year
     */
    public Movie(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
