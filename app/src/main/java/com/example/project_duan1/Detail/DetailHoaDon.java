package com.example.project_duan1.Detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_duan1.Model.HoaDon;
import com.example.project_duan1.R;

public class DetailHoaDon extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_hoadon);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        HoaDon hoaDon = (HoaDon) bundle.get("objec_ten");
        TextView txtmachitiet = findViewById(R.id.txtmachitiet);
        TextView txttenkhachhangchitiet = findViewById(R.id.txttenkhachhangchitiet);
        TextView txtnhanvienchitiet = findViewById(R.id.txtnhanvienchitiet);
        TextView txtgiachitiet = findViewById(R.id.txtgiachitiet);
        TextView txtngaytaochitiet = findViewById(R.id.txtngaytaochitiet);
        TextView txtgiotaochitiet = findViewById(R.id.txtgiotaochitiet);
        Button btntrolai = findViewById(R.id.btntrolai);

        txtmachitiet.setText("Mã HD: "+hoaDon.getMahoadon());
        txttenkhachhangchitiet.setText("Tên khách hàng: "+hoaDon.getTenkhachhang());
        txtnhanvienchitiet.setText("Tên nhân viên: "+hoaDon.getTennhanvien());
        txtgiachitiet.setText("Tổng tiền: "+String.valueOf(hoaDon.getGiatien()));
        txtngaytaochitiet.setText("Ngày tạo: "+hoaDon.getNgaytao());
        txtgiotaochitiet.setText("Giờ tạo: "+hoaDon.getGiotao());

        btntrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
