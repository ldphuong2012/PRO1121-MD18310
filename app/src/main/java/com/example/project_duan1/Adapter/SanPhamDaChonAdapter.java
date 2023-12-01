package com.example.project_duan1.Adapter;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

        holder.setListtenner(new ImateclickListtenner() {
            @Override
            public void OnmateClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong() - 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                        notifyDataSetChanged();
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                        String productId = gioHangList.get(pos).getTensp();
                        String newPrice = gioHangList.get(pos).getGiasp();
                        if (productId != null) {
                            updateFirebaseQuantity(productId, soluongmoi,newPrice);
                        } else {
                            Toast.makeText(context, "Vì ID null lên không cập nhập được dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if (giatri == 2){
                    int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                    gioHangList.get(pos).setSoluong(soluongmoi);
                    notifyDataSetChanged();
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                    String productId = gioHangList.get(pos).getTensp();
                    String newPrice = gioHangList.get(pos).getGiasp();
                    if (productId != null) {
                        updateFirebaseQuantity(productId, soluongmoi,newPrice);
                    } else {
                        Toast.makeText(context, "Vì ID null lên không cập nhập được dữ liệu", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }
    private void updateFirebaseQuantity(String productId, int newQuantity,String newPrice) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThemSanPham/products/" + productId);

        // Cập nhật trực tiếp giá trị số lượng trong sản phẩm
        databaseReference.child("soluong").setValue(newQuantity);
        databaseReference.child("giasp").setValue(newPrice);
    }


    @Override
    public int getItemCount() {

        if (gioHangList != null){
            return gioHangList.size();
        }
        return 0;
    }

    public class SanPhamDaChonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recImage_dachon;
        TextView recName_dachon,recsoluongdachon,recPrice_dachon;
        ImageView giohang_tru,giohang_cong;
        ImateclickListtenner listtenner;
        public SanPhamDaChonViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage_dachon = itemView.findViewById(R.id.recImagetdachon);
            recName_dachon = itemView.findViewById(R.id.recNamedachon);
            recPrice_dachon = itemView.findViewById(R.id.recPricedachon);
            recsoluongdachon = itemView.findViewById(R.id.recsoluongdachon);
            giohang_tru = itemView.findViewById(R.id.giohang_tru);
            giohang_cong = itemView.findViewById(R.id.giohang_cong);

            giohang_tru.setOnClickListener(this);
            giohang_cong.setOnClickListener(this);
        }

        public void setListtenner(ImateclickListtenner listtenner) {
            this.listtenner = listtenner;
        }

        @Override
        public void onClick(View view) {
            if (view == giohang_tru){
                //1 là trừ
                listtenner.OnmateClick(view,getAdapterPosition(),1);
            }
            else if (view == giohang_cong){
                // 2 là cộng
                listtenner.OnmateClick(view,getAdapterPosition(), 2);
            }
        }
    }
}
