package com.example.project_duan1.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_duan1.Model.NhanVien;
import com.example.project_duan1.R;

public class DetailNhanvien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nhanvien);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        NhanVien nhanVien = (NhanVien) bundle.get("objec_ten");

        TextView txtten = findViewById(R.id.txttenchitiet);
        TextView txttuoi = findViewById(R.id.txttuoichitiet);
        TextView txtchucvu = findViewById(R.id.txtchucvuchitiet);
        TextView txtsdt = findViewById(R.id.txtsdtchitiet);
        TextView txtemail = findViewById(R.id.txtemailchitiet);
        Button btntrolai = findViewById(R.id.btntrolai);

        txtten.setText("Tên: " + nhanVien.getTen());
        txttuoi.setText("Tuổi: " + nhanVien.getTuoi());
        txtchucvu.setText("Chức vụ: " + nhanVien.getChucvu());
        txtsdt.setText("Số điện thoại: " + nhanVien.getSdt());
        txtemail.setText("Email: " + nhanVien.getEmail());

        btntrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}