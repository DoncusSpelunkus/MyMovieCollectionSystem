package be;

import java.sql.Date;

public class Movies {
    private final int id;
    private String name;
    private float rating;
    private String filelink;
    private Date lastview;

    public Movies(int id, String name, float rating, String filelink, Date lastview) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
    }
}