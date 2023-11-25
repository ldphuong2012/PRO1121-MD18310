package com.example.project_duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Detail.DetailProduct;
import com.example.project_duan1.Detail.DetailProduct_Main;
import com.example.project_duan1.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter_Main extends RecyclerView.Adapter<ProductAdapter_Main.MainViewHolder> {
    Context context;
    List<Product> productList;

    public ProductAdapter_Main(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_pr_main,parent,false);
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImage()).into(holder.img_pr_main);
        holder.tv_name_pr_main.setText(productList.get(position).getName());

        holder.tv_price_pr_main.setText("Giá: "+ productList.get(position).getPrice()+"đ");
        holder.recCardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailProduct_Main.class);
                intent.putExtra("Image",productList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Name",productList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Price",productList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("TypeProduct",productList.get(holder.getAdapterPosition()).getTypeProduct());
                intent.putExtra("Number",productList.get(holder.getAdapterPosition()).getNumber());
                intent.putExtra("Description",productList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Key",productList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);

            }
        });
        holder.img_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void  searchProduct(ArrayList<Product> searchList){
        productList=searchList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d("zzzz", "Số lương: " +productList.size());
        return productList.size();
    }
    class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView img_pr_main,img_addCart;
        TextView tv_name_pr_main,tv_price_pr_main;
        CardView recCardMain;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pr_main= itemView.findViewById(R.id.img_pr_main);
            tv_name_pr_main= itemView.findViewById(R.id.tv_namePr_main);
            tv_price_pr_main= itemView.findViewById(R.id.tv_pricePr_main);
            img_addCart= itemView.findViewById(R.id.img_addCart);
            recCardMain=itemView.findViewById(R.id.recCardMain);


        }
    }
}
