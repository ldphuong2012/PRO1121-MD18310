package com.example.project_duan1.Adapter;

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

public class DetailDonHangAdapter extends RecyclerView.Adapter<DetailDonHangAdapter.ViewHolder> {
    private List<GioHang> gioHangList;

    public DetailDonHangAdapter(List<GioHang> gioHangList) {
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);

        // Hiển thị thông tin từ đối tượng GioHang trong ViewHolder
        Glide.with(holder.itemView.getContext()).load(gioHang.getImg_pr()).into(holder.Image_DH);
        holder.tvTenSanPham.setText(gioHang.getName_pr());
        holder.tvGiaTien.setText(String.valueOf(gioHang.getPrice_pr()));
        holder.tvSoLuong.setText(String.valueOf(gioHang.getNumber_pr()));
        // ... Hiển thị các thông tin khác tương tự

        // Các xử lý khác trong ViewHolder (nếu cần)
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image_DH;
        TextView tvTenSanPham;
        TextView tvSoLuong;
        TextView tvGiaTien;
        // ... Khai báo các View khác trong ViewHolder (nếu cần)

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Image_DH= itemView.findViewById(R.id.recImage_DH);
            tvTenSanPham = itemView.findViewById(R.id.recName_DH);
            tvGiaTien = itemView.findViewById(R.id.recPrice_DH);
            tvSoLuong = itemView.findViewById(R.id.tv_soLuong_DH);
            // ... Khởi tạo các View khác trong ViewHolder (nếu cần)
        }
    }
}
