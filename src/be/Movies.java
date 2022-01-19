package be;

import java.sql.Date;

public class Movies {

    // This makes sure we have a template for the movies.

    private final int movieID;
    private String name;
    private float rating;
    private float prating;
    private String filelink;
    private Date lastview;
    private int IDinsideList = 0;


    public Movies(int id, String name, float rating, float prating, String filelink, Date lastview) {
        this.movieID = id;
        this.name = name;
        this.rating = rating;
        this.prating = prating;
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

    public float getPRating() {
        return prating;
    }

    public void setPRating(float prating) {
        this.prating = prating;
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

    public String getRatingToString(){
        String ratingString = Float.toString(rating);
        return ratingString;
    }

    public String getPRatingToString(){
        String pRatingString = Float.toString(prating);
        return pRatingString;
    }

    public int getIDinsideList() {
        return IDinsideList;
    }

    public void setIDinsideList(int IDinsideList) {
        this.IDinsideList = IDinsideList;
    }

    public String toString(){
        return name;
    }
}