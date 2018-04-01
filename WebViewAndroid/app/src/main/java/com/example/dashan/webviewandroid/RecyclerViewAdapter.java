package com.example.dashan.webviewandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dashan on 30/3/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{



    private Context mcontext;
    private Book book;

    public RecyclerViewAdapter(Context mcontext, List<Book> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    private List<Book> mData;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(mcontext);
        view=layoutInflater.inflate(R.layout.cardview_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getTitle());
        holder.book_image.setImageResource(mData.get(position).getThumbnail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,WebActivity.class);
                intent.putExtra("BookTitle",mData.get(position).getTitle());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Categorie",mData.get(position).getCategory());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_book_title;
        CardView cardView;
        ImageView book_image;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_book_title=(TextView) itemView.findViewById(R.id.book_title_id);
            book_image=(ImageView) itemView.findViewById(R.id.book_img_id);
            cardView=(CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
