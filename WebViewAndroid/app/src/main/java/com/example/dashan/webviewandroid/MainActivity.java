package com.example.dashan.webviewandroid;

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
        bookList.add(new Book("Sbi Bhim Pay","Categorie Book","https://www.sbi.co.in/portal/web/personal-banking/bhim-sbi-pay",R.drawable.bhim_pay_sbi));
        bookList.add(new Book("Farming Equipments","Equipments","https://www.indiamart.com",R.drawable.equpments));
        bookList.add(new Book("Sbi Account","Bank","https://www.onlinesbi.com",R.drawable.sbi_image));
        bookList.add(new Book("Sbi For Farmers","Loans","https://www.sbi.co.in/portal/web/agriculture-banking/kisan-credit-card-kcc",R.drawable.kisaan_sbi));
        bookList.add(new Book("Prices of harvests","prices","http://www.agriwatch.com",R.drawable.price_pic_farmers));
        bookList.add(new Book("Weather Web","Weather Web","https://darksky.net/forecast",R.drawable.weatherphoto));

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,bookList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(myadapter);

    }
}
