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
    Button btn_add_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_main);
        detailName = findViewById(R.id.detailName_main);
        detailTypePr = findViewById(R.id.detailTypePr_main);
        detailPrice = findViewById(R.id.detailPrice_main);
        detailDes = findViewById(R.id.detailDes_main);
        detailNumber = findViewById(R.id.detailNumber_main);
        detailImg = findViewById(R.id.detailImg_main);
        btn_add_cart = findViewById(R.id.btn_add_cart);
        manggiohang= new ArrayList<>();
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
        initControl();
    }
    private void initControl(){


        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
    }
    private void addtoCart() {
        GioHang objGioHang = new GioHang();
        objGioHang.setImg_pr(imageUrl);
        objGioHang.setName_pr(detailName.getText().toString());
        objGioHang.setPrice_pr(detailPrice.getText().toString());

        // Sử dụng CartManager để thêm mục vào giỏ hàng cục bộ
        CartManager.getInstance().addToCart(objGioHang);

        // Thêm mục vào giỏ hàng Firebase
        addToFirebaseCart(objGioHang);

        Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    public void addToFirebaseCart(GioHang gioHangItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart"); // Thay thế "user_id_here" bằng ID người dùng thực tế hoặc một định danh duy nhất

        String cartItemId = databaseReference.push().getKey(); // Tạo một khóa duy nhất cho mục giỏ hàng

        GioHang cartItem = new GioHang();
        cartItem.setId_pr(cartItemId);
        cartItem.setName_pr(gioHangItem.getName_pr());
        cartItem.setPrice_pr(gioHangItem.getPrice_pr());
        cartItem.setImg_pr(gioHangItem.getImg_pr());

        // Thêm mục giỏ hàng vào Firebase Realtime Database
        databaseReference.child(cartItemId).setValue(cartItem);
    }
}