package be;

import java.sql.Date;

public class Movies {

    private final int movieID;
    private String name;
    private float rating;
    private String filelink;
    private Date lastview;

    public Movies(int id, String name, float rating, String filelink, Date lastview) {
        this.movieID = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }
}