package com.example.project_duan1.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewholder> {
    private List<GioHang> gioHangList;
    Context context;
    private List<GioHang> items;


    public CartAdapter(List<GioHang> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
        items=new ArrayList<>();
    }
    public List<GioHang> getItems() {
        return items;
    }
    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, @SuppressLint("RecyclerView") int position) {
        GioHang objGiohang= gioHangList.get(position);
        holder.recName_cart.setText(objGiohang.getName_pr());
        holder.recPrice_cart.setText(String.valueOf(objGiohang.getPrice_pr()));
        holder.rec_Number_cart.setText(String.valueOf(objGiohang.getNumber_pr()));
        holder.tv_cong_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("Cart");
                String gioHangId = objGiohang.getId_pr(); // Tạo khóa duy nhất cho mục trong Firebase
                Log.d("zzzzzz", "onClick: "+gioHangId);
// Tăng số lượng trong Firebase
                gioHangRef.child(gioHangId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Lấy số lượng hiện tại từ Firebase
                            int currentQuantity = dataSnapshot.child("number_pr").getValue(Integer.class);

                            // Tăng số lượng
                            int newQuantity = currentQuantity + 1;

                            // Cập nhật số lượng mới vào Firebase
                            gioHangRef.child(gioHangId).child("number_pr").setValue(newQuantity)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Số lượng đã được tăng thành công trong Firebase
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý lỗi nếu không thể cập nhật số lượng
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có lỗi khi đọc dữ liệu từ Firebase
                    }
                });


            }
        });
        holder.tv_tru_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("Cart");
                String gioHangId = objGiohang.getId_pr(); // Tạo khóa duy nhất cho mục trong Firebase
                Log.d("zzzzzz", "onClick: " + gioHangId);

                gioHangRef.child(gioHangId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int currentQuantity = dataSnapshot.child("number_pr").getValue(Integer.class);

                            if (currentQuantity > 1) {
                                int newQuantity = currentQuantity - 1;

                                gioHangRef.child(gioHangId).child("number_pr").setValue(newQuantity)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Số lượng đã được giảm thành công trong Firebase
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Xử lý lỗi nếu không thể cập nhật số lượng
                                            }
                                        });

                                // Cập nhật số lượng mới trên giao diện
                                objGiohang.setNumber_pr(newQuantity);
                                holder.rec_Number_cart.setText(String.valueOf(objGiohang.getNumber_pr()));
                            } else {
                                // Xóa khỏi giỏ hàng nếu số lượng đã là 0
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Bạn có chắc chắn muốn xóa khỏi giỏ hàng ? ");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        gioHangList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, gioHangList.size());

                                        // Xóa từ Firebase
                                        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
                                        cartRef.child(objGiohang.getId_pr()).removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Xóa thành công
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Xóa thất bại, xử lý lỗi
                                                    }
                                                });

                                        dialog.dismiss();
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
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có lỗi khi đọc dữ liệu từ Firebase
                    }
                });
            }
        });
        Glide.with(holder.itemView.getContext()).load(objGiohang.getImg_pr()).into(holder.img_cart);


    }

    @Override
    public int getItemCount() {
        Log.d("Cart", "getItemCount: "+gioHangList.size());
        return gioHangList.size();
    }

    static  class CartViewholder extends RecyclerView.ViewHolder{
        TextView recName_cart,recPrice_cart,rec_Number_cart,tv_tru_cart,tv_cong_cart;
        ImageView img_cart;


        public CartViewholder(@NonNull View itemView) {
            super(itemView);
            img_cart= itemView.findViewById(R.id.recImage_cart);
            recName_cart= itemView.findViewById(R.id.recName_cart);
            recPrice_cart= itemView.findViewById(R.id.recPrice_cart);
            rec_Number_cart= itemView.findViewById(R.id.recNumber_cart);
            tv_tru_cart= itemView.findViewById(R.id.tv_tru_cart);
            tv_cong_cart= itemView.findViewById(R.id.tv_cong_cart);


        }
    }
}
