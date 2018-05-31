package com.example.dashan.introslider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private IntroSliderManager introSliderManager;

    private TextView[] dots;
    private LinearLayout linearLayout;

    private Button mNext,mSkip;

    private int[] layouts;

    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introSliderManager=new IntroSliderManager(this);
        if(!introSliderManager.Check()){
            introSliderManager.setFirst(false);
            Intent i=new Intent(MainActivity.this,Login_Screen.class);
            startActivity(i);
            finish();
        }

        if(Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        viewPager=(ViewPager)findViewById(R.id.view_pager);
        linearLayout=(LinearLayout)findViewById(R.id.layout_dots);
        mSkip=(Button)findViewById(R.id.button_skip);
        mNext=(Button)findViewById(R.id.button_next);
        layouts=new int[]{R.layout.activity_screen_1,
                R.layout.activity_screen_2,
                R.layout.activity_screen_3,
                R.layout.activity_screen_4,
                R.layout.activity_screen_5};
        addBottomDots(0);
        changeStatusbarColor();
        viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListner);


        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Login_Screen.class);
                startActivity(intent);
                finish();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current=getItem(+1);
                if(current<layouts.length){
                    viewPager.setCurrentItem(current);
                }
                else{
                    Intent intent=new Intent(MainActivity.this,Login_Screen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void addBottomDots(int postions){
        dots=new TextView[layouts.length];
        int[] colorActive=getResources().getIntArray(R.array.dot_active);
        int[] colorInactive=getResources().getIntArray(R.array.dot_inactive);
        linearLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[postions]);
            linearLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[postions].setTextColor(colorActive[postions]);
        }
    }
    private int getItem(int i){
        return viewPager.getCurrentItem()+1;
    }

    ViewPager.OnPageChangeListener viewListner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addBottomDots(position);
            if(position==layouts.length-1){
                mNext.setText("Proceed");
                mSkip.setVisibility(View.GONE);
            }
            else{
                mNext.setText("NEXT");
                mNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

   private void changeStatusbarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
   }

    public class ViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View v=(View)object;
            container.removeView(v);
        }
    }
}
