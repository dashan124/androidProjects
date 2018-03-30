package com.example.dashan.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Book> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList=new ArrayList<>();
        bookList.add(new Book("Little Rabit","Categorie Book","Description",R.drawable.firstbook));
        bookList.add(new Book("Titanic","Adult","Description",R.drawable.secondbook));
        bookList.add(new Book("Renewation of Love","Love","Description",R.drawable.thirdbook));
        bookList.add(new Book("Romeo and Juliot","Love","Description",R.drawable.fourthbook));
        bookList.add(new Book("Hamlet","Categorie Book","Description",R.drawable.fifth));
        bookList.add(new Book("Little Red","Categorie Book","Description",R.drawable.sixbook));
        bookList.add(new Book("Little Rabit","Categorie Book","Description",R.drawable.firstbook));
        bookList.add(new Book("Titanic","Adult","Description",R.drawable.secondbook));
        bookList.add(new Book("Renewation of Love","Love","Description",R.drawable.thirdbook));
        bookList.add(new Book("Romeo and Juliot","Love","Description",R.drawable.fourthbook));
        bookList.add(new Book("Hamlet","Categorie Book","Description",R.drawable.fifth));
        bookList.add(new Book("Little Red","Categorie Book","Description",R.drawable.sixbook));


        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,bookList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(myadapter);

    }
}
