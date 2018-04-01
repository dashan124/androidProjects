package com.example.dashan.webviewandroid;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    private WebView mywebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mywebview=(WebView) findViewById(R.id.webView);
        String Title="";
        String Descrip="";
        String Categorie="";
        Intent intent=getIntent();
        try {
            Title = intent.getExtras().getString("Title");
            Log.d("TAG", Title);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            Descrip = intent.getExtras().getString("Description");
            Log.d("Description", Descrip);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            Categorie = intent.getExtras().getString("Categorie");
            Log.d("Categorie", Categorie);
        }catch (Exception e){
            e.printStackTrace();
        }
        int image=intent.getExtras().getInt("Thumbnail");
        //Toast.makeText(this,Descrip,Toast.LENGTH_SHORT).show();

        WebSettings webSettings=mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        mywebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mywebview.setScrollbarFadingEnabled(false);
        mywebview.loadUrl(Descrip);
        //mywebview.loadUrl("https://www.amazon.com");
        mywebview.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(mywebview.canGoBack()){
            mywebview.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        if(id==R.id.ic_icon_back){
            Intent intent=new Intent(WebActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
