package com.example.project_duan1.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.Manager.CartManager;
import com.example.project_duan1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailProduct_Main extends AppCompatActivity {
    TextView detailName, detailTypePr, detailPrice, detailDes, detailNumber;
    ImageView detailImg;
    List<GioHang> manggiohang;

    String key = "";
    String imageUrl = "";


    DatabaseReference cartRef; // Tham chiếu đến nút "Cart" trong Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_main);

        cartRef = FirebaseDatabase.getInstance().getReference("Cart"); // Khởi tạo tham chiếu đến nút "Cart" trong Firebase Realtime Database

        detailName = findViewById(R.id.detailName_main);
        detailTypePr = findViewById(R.id.detailTypePr_main);
        detailPrice = findViewById(R.id.detailPrice_main);
        detailDes = findViewById(R.id.detailDes_main);
        detailNumber = findViewById(R.id.detailNumber_main);
        detailImg = findViewById(R.id.detailImg_main);

        manggiohang = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailName.setText(bundle.getString("Name"));
            detailTypePr.setText(bundle.getString("TypeProduct"));
            detailPrice.setText(bundle.getString("Price"));
            detailDes.setText(bundle.getString("Description"));
            detailNumber.setText(bundle.getString("Number"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImg);
        }


    }




}