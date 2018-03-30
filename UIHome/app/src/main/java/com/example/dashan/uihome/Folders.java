package com.example.dashan.uihome;

import android.widget.TextView;

/**
 * Created by dashan on 30/3/18.
 */

public class Folders {
    private String Title;
    private String Category;
    private String Description;
    private int Thumbnail;
    public Folders(){

    }
    public Folders(String title,String category,String description,int thumbnail){
        Title=title;
        Category=category;
        Description=description;
        Thumbnail=thumbnail;
    }
    public String getTitle(){
        return Title;
    }
    public String getCategory(){
        return Category;
    }
    public String getDescription(){
        return Description;
    }
    public int getThumbnail(){
        return Thumbnail;
    }
    public void setTitle(String title){
        Title=title;
    }
    public void setCategory(String category){
        Category=category;
    }
    public void setDescription(String description){
        Description=description;
    }
    public void setThumbnail(int thumbnail){
        Thumbnail=thumbnail;
    }

}
