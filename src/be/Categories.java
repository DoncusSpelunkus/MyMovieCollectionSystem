package be;

import java.util.ArrayList;

public class Categories {

    private String name;
    private final int id;
    private list<Categories> categorieslist = new ArrayList<>();

    public Categories(String name, int id){
        this.name = name;
        this.id = id;
    }
}
