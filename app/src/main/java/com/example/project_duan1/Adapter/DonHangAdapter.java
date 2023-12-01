package com.example.project_duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.DTO.Bill;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.Detail.DetailDonHang;
import com.example.project_duan1.R;

import java.io.Serializable;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {
    Context context;
    List<Bill > billList;

    public DonHangAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xndh_item, parent, false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        Bill objBill= billList.get(position);
        holder.tv_date.setText(objBill.getFormatdate());
        holder.tv_name.setText(objBill.getFullname());
        holder.tv_status.setText(objBill.isConfirmed() ? "Đã xác nhận" : "Chưa xác nhận" );
        holder.tv_price.setText(String.valueOf(objBill.getTotal())+"đ");
        holder.recCard_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailDonHang.class);
                intent.putExtra("Fullname", objBill.getFullname() );
                intent.putExtra("Address", objBill.getAddress());
                intent.putExtra("Subtotal", objBill.getSubtotal());
                intent.putExtra("Delivery", objBill.getDelivery());
                intent.putExtra("TotalTax", objBill.getTotalTax());
                intent.putExtra("Total", objBill.getTotal());
                intent.putExtra("GioHangList", (Serializable) objBill.getGioHangList());
                intent.putExtra("IsCOD", objBill.isCOD());
                intent.putExtra("IsATM", objBill.isATM());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_name,tv_status,tv_price;
        CardView recCard_dh;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date_order);
            tv_name=itemView.findViewById(R.id.tv_name_order);
            tv_status=itemView.findViewById(R.id.tv_status_order);
            tv_price=itemView.findViewById(R.id.tv_price_order);
            recCard_dh=itemView.findViewById(R.id.recCard_dh);
        }
    }
}
