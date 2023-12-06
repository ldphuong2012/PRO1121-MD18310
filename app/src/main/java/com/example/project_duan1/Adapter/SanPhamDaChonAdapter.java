package com.example.project_duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.project_duan1.Manager.ImateclickListtenner;
import com.example.project_duan1.Model.EventBus.TinhTongEvent;
import com.example.project_duan1.Model.GioHangHoaDon;
import com.example.project_duan1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SanPhamDaChonAdapter extends RecyclerView.Adapter<SanPhamDaChonAdapter.SanPhamDaChonViewHolder>{
    private ArrayList<GioHangHoaDon> gioHangList;
    Context context;

    public SanPhamDaChonAdapter(ArrayList<GioHangHoaDon> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public SanPhamDaChonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.danhsach_sanphamdachon,parent,false);

        return new SanPhamDaChonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamDaChonViewHolder holder, int position) {

        GioHangHoaDon gioHangHoaDon = gioHangList.get(position);
        if (gioHangHoaDon == null){
            return;
        }
        Glide.with(context).load(gioHangList.get(position).getHinhsp()).into(holder.recImage_dachon);
        holder.recName_dachon.setText(gioHangList.get(position).getTensp());
        holder.recsoluongdachon.setText(String.valueOf(gioHangList.get(position).getSoluong()));
        holder.recPrice_dachon.setText(gioHangList.get(position).getGiasp());


        holder.giohang_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThemSanPham");
                String gioHangID = gioHangHoaDon.getIdsp();//Tạo khóa duy nhất cho mục trong Firebase
                Log.d("zzzzzz", "onClick: "+gioHangID);
                // Tăng số lượng trong Firebase
                databaseReference.child(gioHangID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            // Lấy số lượng hiện tại từ Firebase
                            int currentQuantity = snapshot.child("soluong").getValue(Integer.class);
                            // Tăng số lượng
                            int newQuantity = currentQuantity + 1;
                            // Cập nhật số lượng mới vào Firebase
                            databaseReference.child(gioHangID).child("soluong").setValue(newQuantity)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Số lượng đã được tăng thành công trong Firebase
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý lỗi nếu không thể cập nhật số lượng
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        // Xử lý lỗi nếu có lỗi khi đọc dữ liệu từ Firebase
                    }
                });
            }
        });
        holder.giohang_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThemSanPham");
                String gioHangID = gioHangHoaDon.getIdsp();//Tạo khóa duy nhất cho mục trong Firebase
                Log.d("zzzzzz", "onClick: "+gioHangID);
                databaseReference.child(gioHangID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            int currentQuantity = snapshot.child("soluong").getValue(Integer.class);
                            if (currentQuantity > 1) {
                                int newQuantity = currentQuantity - 1;
                                databaseReference.child(gioHangID).child("soluong").setValue(newQuantity)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // Số lượng đã được giảm thành công trong Firebase
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Xử lý lỗi nếu không thể cập nhật số lượng
                                            }
                                        });
                                // Cập nhật số lượng mới trên giao diện
                                holder.recsoluongdachon.setText(String.valueOf(gioHangHoaDon.getSoluong()));
                            }
                            else {
                                // Xóa khỏi giỏ hàng nếu số lượng đã là 0
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Bạn có chắc muốn xóa sản phẩm không ?");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        gioHangList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,gioHangList.size());

                                        //Xóa từ Firebase
                                        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("ThemSanPham");
                                        cartRef.child(gioHangHoaDon.getIdsp()).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        //Xóa thành công
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        //Xóa thất bại
                                                    }
                                                });
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton("Khônng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        //Xử lý nỗi dữ liệu đọc từ Firebase
                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {

        if (gioHangList != null){
            return gioHangList.size();
        }
        return 0;
    }

    public class SanPhamDaChonViewHolder extends RecyclerView.ViewHolder {

        ImageView recImage_dachon;
        TextView recName_dachon,recsoluongdachon,recPrice_dachon;
        ImageView giohang_tru,giohang_cong;
        public SanPhamDaChonViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage_dachon = itemView.findViewById(R.id.recImagetdachon);
            recName_dachon = itemView.findViewById(R.id.recNamedachon);
            recPrice_dachon = itemView.findViewById(R.id.recPricedachon);
            recsoluongdachon = itemView.findViewById(R.id.recsoluongdachon);
            giohang_tru = itemView.findViewById(R.id.giohang_tru);
            giohang_cong = itemView.findViewById(R.id.giohang_cong);

        }
    }
}
