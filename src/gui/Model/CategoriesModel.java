package gui.Model;

import be.Categories;
import be.Movies;
import bll.BLLFacade;
import bll.BLLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

public class CategoriesModel {

    private ObservableList<Categories> categories;
    private BLLFacade logiclayer;

    public CategoriesModel(){
        categories = FXCollections.observableArrayList();
        logiclayer = new BLLManager();
    }
    
    public void addCategory(String name){
        logiclayer.addCategory(name);
    }

    public ObservableList<Categories> getAllCategories() {
        categories = FXCollections.observableArrayList();
        categories.addAll(logiclayer.getAllCategories());
        return categories;
    }

    public void deleteCategory(Categories selectedCategory){
        logiclayer.deleteCategory(selectedCategory);
    }

    public void editCategory(Categories selectedCategory, String name){
        logiclayer.editCategory(selectedCategory, name);
    }
    
}

