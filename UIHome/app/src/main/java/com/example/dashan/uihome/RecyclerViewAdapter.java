package com.example.dashan.uihome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dashan on 30/3/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

   private Context mContext;
   private List<Folders> mData;

    public RecyclerViewAdapter(Context mContext,List<Folders> mData) {
        this.mContext = mContext;
        this.mData=mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        view=layoutInflater.inflate(R.layout.cardview_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_book_title.setText(mData.get(position).getTitle());
        holder.book_image.setImageResource(mData.get(position).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_book_title;
        ImageView book_image;
        public MyViewHolder(View itemView){
            super(itemView);
            tv_book_title=(TextView) itemView.findViewById(R.id.title_textview);
            book_image=(ImageView) itemView.findViewById(R.id.image_thumbnail);
        }
    }
}
