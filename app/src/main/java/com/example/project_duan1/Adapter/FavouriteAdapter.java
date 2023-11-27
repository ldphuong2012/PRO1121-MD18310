package com.example.project_duan1.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.Favourite;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    List<Favourite> favouriteList;
    Context context;

    public FavouriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Favourite objFavourite= favouriteList.get(position);
        holder.recName_favourite.setText(objFavourite.getName_pr_favourite());
        holder.recPrice_favourite.setText(objFavourite.getPrice_pr_favourite());
        Glide.with(holder.itemView.getContext()).load(objFavourite.getImg_pr_favourite()).into(holder.img_favourite);

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+favouriteList.size());
        return favouriteList.size();
    }

    static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView recName_favourite,recPrice_favourite;
        ImageView img_favourite,img_delete_favourite;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            img_favourite= itemView.findViewById(R.id.img_favorite);
            img_delete_favourite= itemView.findViewById(R.id.img_delete_pr_favourite);
            recName_favourite= itemView.findViewById(R.id.recName_favourite);
            recPrice_favourite= itemView.findViewById(R.id.recPrice_favourite);
        }
    }
}
