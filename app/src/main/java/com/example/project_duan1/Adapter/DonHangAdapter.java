package com.example.project_duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.DTO.Bill;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.Detail.DetailDonHang;
import com.example.project_duan1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
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

// Truyền các giá trị thông qua putExtra()
                intent.putExtra("fullName", objBill.getFullname());
                intent.putExtra("address", objBill.getAddress());
                intent.putExtra("subtotal", objBill.getSubtotal());
                intent.putExtra("delivery", objBill.getDelivery());
                intent.putExtra("totalTax", objBill.getTotalTax());
                intent.putExtra("total", objBill.getTotal());
                intent.putExtra("isCOD", objBill.isCOD());
                intent.putExtra("isATM", objBill.isATM());
                intent.putExtra("giohangList", (Serializable) objBill.getGioHangList());


// Khởi chạy Activity mới
                context.startActivity(intent);
            }
        });
        holder.recCard_dh.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn xác nhận đơn hàng ? ");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đánh dấu trạng thái đã xác nhận
                        objBill.setConfirmed(true);

                        // Lấy reference đến nút đơn hàng trong Realtime Database
                        // Lấy reference đến nút đơn hàng trong Realtime Database
                        // Lấy reference đến nút đơn hàng trong Realtime Database
                        DatabaseReference locationRef = FirebaseDatabase.getInstance().getReference("Orders").child(objBill.getId_bill());

// Cập nhật trạng thái đã xác nhận trong Realtime Database
                        locationRef.child("confirmed").setValue(true)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Cập nhật thành công, thực hiện các thao tác khác nếu cần
                                        holder.tv_status.setText("Đã xác nhận");
                                        Toast.makeText(context, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Xảy ra lỗi trong quá trình cập nhật
                                        Toast.makeText(context, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
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
