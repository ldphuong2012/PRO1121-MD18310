package com.example.project_duan1.Detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project_duan1.Adapter.DonHangAdapter;
import com.example.project_duan1.DTO.Bill;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DetailDonHang extends AppCompatActivity {
RecyclerView rec_DH;
TextView tv_subtotal_d_DH,tv_delivery_d_DH,tv_totaltax_d_DH,tv_total_d_DH;
EditText ed_fullname_DH,ed_address_DH;
CheckBox cb_cod_DH,cb_atm_DH;
List<Bill> billList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_don_hang);
        rec_DH=findViewById(R.id.recycler_DH);
        tv_subtotal_d_DH=findViewById(R.id.tv_subtotal_d_DH);
        tv_delivery_d_DH=findViewById(R.id.tv_delivery_d_DH);
        tv_totaltax_d_DH=findViewById(R.id.tv_totaltax_d_DH);
        tv_total_d_DH=findViewById(R.id.tv_total_d_DH);
        ed_fullname_DH=findViewById(R.id.ed_fullname_DH);
        ed_address_DH=findViewById(R.id.ed_address_DH);
        cb_cod_DH=findViewById(R.id.cb_cod_DH);
        cb_atm_DH=findViewById(R.id.cb_atm_DH);








    }}

