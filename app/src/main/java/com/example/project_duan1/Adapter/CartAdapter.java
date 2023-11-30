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
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewholder> {
    private List<GioHang> gioHangList;
    Context context;

    public CartAdapter(List<GioHang> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, int position) {
        GioHang objGiohang= gioHangList.get(position);
        holder.recName_cart.setText(objGiohang.getName_pr());
        holder.recPrice_cart.setText(String.valueOf(objGiohang.getPrice_pr()));
        Glide.with(holder.itemView.getContext()).load(objGiohang.getImg_pr()).into(holder.img_cart);


    }

    @Override
    public int getItemCount() {
        Log.d("Cart", "getItemCount: "+gioHangList.size());
        return gioHangList.size();
    }

    static  class CartViewholder extends RecyclerView.ViewHolder{
        TextView recName_cart,recPrice_cart;
        ImageView img_cart;


        public CartViewholder(@NonNull View itemView) {
            super(itemView);
            img_cart= itemView.findViewById(R.id.recImage_cart);
            recName_cart= itemView.findViewById(R.id.recName_cart);
            recPrice_cart= itemView.findViewById(R.id.recPrice_cart);


        }
    }
}
