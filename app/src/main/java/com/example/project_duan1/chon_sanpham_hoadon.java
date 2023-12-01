package com.example.project_duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.project_duan1.Adapter.chon_sanphamAdapter;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Model.GioHangHoaDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chon_sanpham_hoadon extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<Product> productList;
    private chon_sanphamAdapter sanphamAdapter;
    CardView recCardthemhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_sanpham_hoadon);
        recyclerView = findViewById(R.id.recycleViewthemhoadon1);
        searchView = findViewById(R.id.searchViewthemhoadon1);
        searchView.clearFocus();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        productList = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ThemSanPham");
        sanphamAdapter = new chon_sanphamAdapter(this, productList, new chon_sanphamAdapter.ProductClickListener() {
            @Override
            public void onProductClick(Product selectedProduct) {

                // Tạo một đối tượng Firebase Database Reference đến node bạn muốn lưu trữ dữ liệu
                DatabaseReference productsRef = myRef.child("products");

                // Tạo một đối tượng Firebase Database Reference cho sản phẩm được chọn
                DatabaseReference selectedProductRef = productsRef.push();

                // Đặt dữ liệu cho sản phẩm được chọn
                selectedProductRef.child("tensp").setValue(selectedProduct.getName());
                selectedProductRef.child("giasp").setValue(selectedProduct.getPrice());
                selectedProductRef.child("hinhsp").setValue(selectedProduct.getImage());
                //Thêm các thuộc tính khác nếu cần
                selectedProductRef.child("soluong").setValue(1);
                // Hiển thị thông báo hoặc thực hiện các hành động khác nếu cần
                Toast.makeText(chon_sanpham_hoadon.this, "Sản phẩm được thêm vào Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        //tạo ra mảng mới để chứa dữ liệu chọn
        recyclerView.setAdapter(sanphamAdapter);
        getListProduct();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText.toString().toLowerCase();
                sanphamAdapter.getFilter().filter(searchText);
                return false;
            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchList(newText);
//                return true;
//            }
//        });


    }



//    public void searchList(String text) {
//        ArrayList<Product> searchList = new ArrayList<>();
//        for (Product dataclass : productList) {
//            if (dataclass.getName().toLowerCase().contains(text.toLowerCase())) {
//                searchList.add(dataclass);
//            }
//        }
//        sanphamAdapter.searchProduct(searchList);
//    }

    private void getListProduct (){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Product product = snapshot1.getValue(Product.class);
                    productList.add(product);
                }

                sanphamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(chon_sanpham_hoadon.this, "Khong tai duoc du lieu firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
