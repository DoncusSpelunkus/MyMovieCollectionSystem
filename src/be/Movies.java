package be;

import java.sql.Date;

public class Movies {

    private final int movieID;
    private String name;
    private float rating;
    private String filelink;
    private Date lastview;

    private Categories category1;
    private Categories category2;
    private Categories category3;

    public Movies(int id, String name, float rating, String filelink, Date lastview) {
        this.movieID = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
    }

    public Categories getCategory1() {
        return category1;
    }

    public void setCategory1(Categories category1) {
        this.category1 = category1;
    }

    public Categories getCategory2() {
        return category2;
    }

    public void setCategory2(Categories category2) {
        this.category2 = category2;
    }

    public Categories getCategory3() {
        return category3;
    }

    public void setCategory3(Categories category3) {
        this.category3 = category3;
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

    public String getRatingToString(){
        String ratingString = Float.toString(rating);
        return ratingString;
    }
}