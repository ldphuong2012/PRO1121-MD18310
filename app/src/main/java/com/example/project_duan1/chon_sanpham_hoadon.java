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

        sanphamAdapter = new chon_sanphamAdapter(this,productList);

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



    }


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
