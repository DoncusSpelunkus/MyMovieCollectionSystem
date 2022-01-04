package be;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    private String name;
    private final int categoryID;

    public Categories(String name, int id){
        this.name = name;
        this.categoryID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public List<Categories> getCategorieslist() {
        return categorieslist;
    }

    public void setCategorieslist(List<Categories> categorieslist) {
        this.categorieslist = categorieslist;
    }

    private List<Categories> categorieslist = new ArrayList<>();

}
