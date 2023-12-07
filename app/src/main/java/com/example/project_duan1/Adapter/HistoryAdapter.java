package com.example.project_duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.DTO.Bill;
import com.example.project_duan1.Detail.DetailDonHang;
import com.example.project_duan1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    Context context;
    List<Bill> billList;

    public HistoryAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {




        Bill objBill = billList.get(position);
        holder.tv_date.setText(objBill.getFormatdate());
        holder.tv_name.setText(objBill.getFullname());
        holder.tv_status.setText(objBill.isConfirmed() ? "Đã xác nhận" : "Chưa xác nhận");
        holder.tv_price.setText(String.valueOf(objBill.getTotal()) + "đ");
        holder.recCard_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent
                Intent intent = new Intent(context, DetailDonHang.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("gioHangList", (Serializable) objBill.getGioHangList());

                // Đưa Bundle vào Intent
                intent.putExtras(bundle);

// Truyền các giá trị thông qua putExtra()
                intent.putExtra("fullName", objBill.getFullname());
                intent.putExtra("address", objBill.getAddress());
                intent.putExtra("subtotal", objBill.getSubtotal());
                intent.putExtra("delivery", objBill.getDelivery());
                intent.putExtra("totalTax", objBill.getTotalTax());
                intent.putExtra("total", objBill.getTotal());
                intent.putExtra("isCOD", objBill.isCOD());
                intent.putExtra("isATM", objBill.isATM());



// Khởi chạy Activity mới
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_name,tv_status,tv_price;
        CardView recCard_dh;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date_history);
            tv_name=itemView.findViewById(R.id.tv_name_history);
            tv_status=itemView.findViewById(R.id.tv_status_history);
            tv_price=itemView.findViewById(R.id.tv_price_history);
            recCard_dh=itemView.findViewById(R.id.recCard_history);
        }
    }

}
