package com.example.project_duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.Model.TopSanPham;
import com.example.project_duan1.R;

import java.util.ArrayList;

public class TopSanPhamAdapter extends RecyclerView.Adapter<TopSanPhamAdapter.TopViewHolder>{
    private ArrayList<TopSanPham> topSanPhams;
    private Context context;

    public TopSanPhamAdapter(ArrayList<TopSanPham> topSanPhams, Context context) {
        this.topSanPhams = topSanPhams;
        this.context = context;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_banchay, parent, false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        TopSanPham topSanPham = topSanPhams.get(position);
        if (topSanPham == null){
            return;
        }
        holder.txtTenSanPham.setText("Tên sản phẩm: "+topSanPham.getTensp());
        holder.txtSoLuongBan.setText("Số lượng bán: "+topSanPham.getSoluong());

    }

    @Override
    public int getItemCount() {
        if (topSanPhams != null){
            return topSanPhams.size();
        }
        return 0;
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham, txtSoLuongBan;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtSoLuongBan = itemView.findViewById(R.id.txtSoLuongBan);
        }
    }
}
