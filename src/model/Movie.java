package model;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private int duration;
    private double budget;

    public Movie() {}

    public Movie(String title, String genre, int duration, double budget) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.budget = budget;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + title + " (" + genre + ") | " + duration + "min | $" + budget;
    }
}