package com.example.project_duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Detail.DetailNhanvien;
import com.example.project_duan1.Manager.HoaDonManager;
import com.example.project_duan1.Model.GioHangHoaDon;
import com.example.project_duan1.Model.NhanVien;
import com.example.project_duan1.R;
import com.example.project_duan1.add_hoadon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class chon_sanphamAdapter extends RecyclerView.Adapter<chon_sanphamAdapter.chonSanPhamViewHolder> implements Filterable {

    private ArrayList<Product> mListProduct;
    private ArrayList<Product> mListProductOld;
    Context context;

    public chon_sanphamAdapter(Context context, ArrayList<Product> mListProduct) {
        this.mListProduct = mListProduct;
        this.context = context;
        this.mListProductOld = mListProduct;

    }


    @NonNull
    @Override
    public chonSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.danhsach_sanpham_hoadon,parent,false);

        return new chonSanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chonSanPhamViewHolder holder, int position) {

        final Product product = mListProduct.get(position);
        if (product == null){
            return;
        }
        Glide.with(context).load(mListProduct.get(position).getImage()).into(holder.recImage_themHoaDon);
        holder.recName_themHoaDon.setText(mListProduct.get(position).getName());
        holder.recNumber_themHoaDon.setText(mListProduct.get(position).getNumber());
        holder.recPrice_themHoaDon.setText(mListProduct.get(position).getPrice());
        holder.reclayoutthemspHoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoHoaDon(product);
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,add_hoadon.class));
            }
        });
        //Intent dữ liệu sang add_hoadon khi click vào san pham
//        holder.reclayoutthemspHoadon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickGotoDetail(product);
//            }
//        });

    }
//    private void onClickGotoDetail(Product product){
//        Intent intent = new Intent(context, add_hoadon.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("objec_name",product);
//        intent.putExtras(bundle);
//        context.startActivity(intent);
//    }

    @Override
    public int getItemCount() {
        if (mListProduct != null){
            return mListProduct.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListProduct = mListProductOld;
                }
                else {
                    ArrayList<Product> list = new ArrayList<>();
                    for (Product product : mListProductOld){
                        if (product.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(product);
                        }
                    }
                    mListProduct = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListProduct;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListProduct = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class chonSanPhamViewHolder extends RecyclerView.ViewHolder{

        ImageView recImage_themHoaDon;
        TextView recName_themHoaDon,recNumber_themHoaDon,recPrice_themHoaDon;

        CardView reclayoutthemspHoadon;
        public chonSanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage_themHoaDon=itemView.findViewById(R.id.recImagethemhoadon);
            recName_themHoaDon=itemView.findViewById(R.id.recNamethemhoadon);
            recNumber_themHoaDon=itemView.findViewById(R.id.recNumberthemhoadon);
            recPrice_themHoaDon=itemView.findViewById(R.id.recPricethemhoadon);
            reclayoutthemspHoadon = itemView.findViewById(R.id.recCardthemhoadon);
//            reclayoutthemspHoadon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && productClickListener != null) {
//                        Product selectedProduct = mListProduct.get(position);
//                        productClickListener.onProductClick(selectedProduct);
//                    }
//                }
//            });
        }
    }
    private void addtoHoaDon(Product product){
        GioHangHoaDon existingItem = HoaDonManager.getInstance().getHoaDonByName(product.getName());
        if (existingItem != null){
            //Tăng số lượng của sản phẩm đã có trong giỏ hàng
            int currentQuantity = existingItem.getSoluong();
            existingItem.setSoluong(currentQuantity + 1);
            // Cập nhật giỏ hàng cục bộ
            HoaDonManager.getInstance().updateSanPhamHD(existingItem);
            // Cập nhật giỏ hàng trên Firebase12
            updateFirebaseCartItem(existingItem);
        }
        else {
            GioHangHoaDon gioHangHoaDon = new GioHangHoaDon();
            gioHangHoaDon.setIdsp(product.getID_pr());
            gioHangHoaDon.setHinhsp(product.getImage());
            gioHangHoaDon.setTensp(product.getName());
            gioHangHoaDon.setGiasp(product.getPrice());
            gioHangHoaDon.setSoluong(1);

            // Thêm vào giỏ hàng cục bộ
            HoaDonManager.getInstance().addSanPhamHD(gioHangHoaDon);
            // Thêm vào giỏ hàng trên Firebase
            addToFirebaseCart(gioHangHoaDon);
        }
    }

    private void addToFirebaseCart(GioHangHoaDon objGiohang) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThemSanPham");

        String cartItemId = objGiohang.getIdsp(); // Get the cart item ID from the GioHangHoaDon object


        // Thêm thông tin sản phẩm vào Firebase Realtime Database
        databaseReference.child(cartItemId).setValue(objGiohang);
    }
    private void updateFirebaseCartItem(GioHangHoaDon objGiohang) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ThemSanPham");

        // Sử dụng cartItemId để cập nhật thông tin sản phẩm trong Firebase Realtime Database
        databaseReference.child(objGiohang.getIdsp()).setValue(objGiohang);
    }

}
