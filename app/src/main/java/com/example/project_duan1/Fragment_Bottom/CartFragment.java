package com.example.project_duan1.Fragment_Bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.Adapter.CartAdapter;
import com.example.project_duan1.DTO.Bill;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    RecyclerView rec_Cart;
    TextView tv_subtotal,tv_delivery,tv_tax,tv_total;
    ScrollView scrollView;
    CartAdapter adapter;
    List<GioHang> gioHangList;
    CheckBox cb_cod,cb_atm;
    Button btn_order;
    EditText ed_fullname,ed_address;


    public CartFragment() {


    }


    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec_Cart = view.findViewById(R.id.recycler_Cart);
        tv_subtotal = view.findViewById(R.id.tv_subtotal_d);
        tv_delivery = view.findViewById(R.id.tv_delivery_d);
        tv_tax = view.findViewById(R.id.tv_totaltax_d);
        tv_total = view.findViewById(R.id.tv_total_d);
        scrollView = view.findViewById(R.id.scoll_view_cart);
        gioHangList = new ArrayList<>();
        adapter = new CartAdapter(gioHangList, getContext());
        rec_Cart.setAdapter(adapter);
        cb_atm = view.findViewById(R.id.cb_atm);
        cb_cod = view.findViewById(R.id.cb_cod);
        ed_fullname = view.findViewById(R.id.ed_fullname);
        ed_address = view.findViewById(R.id.ed_address);

        btn_order = view.findViewById(R.id.btn_order);


        cb_cod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_atm.setChecked(false);
                } else {

                }
            }
        });
        cb_atm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_cod.setChecked(false);
                } else {

                }
            }
        });
        DatabaseReference favouriteRef = FirebaseDatabase.getInstance().getReference("Cart");

        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gioHangList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GioHang gioHangItem = snapshot.getValue(GioHang.class);
                    gioHangList.add(gioHangItem);
                }

                adapter.notifyDataSetChanged(); // Cập nhật RecyclerView khi có thay đổi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        CalculatorTotal();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = ed_fullname.getText().toString();
                String address = ed_address.getText().toString();
                double subtotal = Double.parseDouble(tv_subtotal.getText().toString().replace("đ", ""));
                double delivery = Double.parseDouble(tv_delivery.getText().toString().replace("đ", ""));
                double totalTax = Double.parseDouble(tv_tax.getText().toString().replace("đ", ""));
                double total = Double.parseDouble(tv_total.getText().toString().replace("đ", ""));

                boolean isCOD = cb_cod.isChecked(); // Lấy trạng thái của checkbox COD
                boolean isATM= cb_atm.isChecked();
                Calendar calendar = Calendar.getInstance();
                Date createdAt = calendar.getTime();

                // Format the date as "dd/MM/yyyy"
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(createdAt);
                boolean isConfirmed = false;

                String id_bill = UUID.randomUUID().toString(); // Generate a unique ID for the bill

                Bill order = new Bill(id_bill, fullname, address, subtotal, delivery, totalTax, total, gioHangList, isCOD, isATM, formattedDate,isConfirmed);
                DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
                DatabaseReference newOrderRef = ordersRef.push();
                newOrderRef.setValue(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Xóa giỏ hàng sau khi tạo đơn hàng thành công
                                clearCart();
                                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                ed_fullname.setText("");
                                ed_address.setText("");
                                cb_cod.setChecked(false);
                                cb_atm.setChecked(false);
                                // Chuyển sang màn hình hoặc thực hiện hành động khác
                                // ...
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Xử lý khi tạo đơn hàng thất bại
                                // ...
                            }
                        });

            }
        });

    }


    private double CalculatorTotal() {
        double delivery = 30000;

        DatabaseReference gioHangRef = FirebaseDatabase.getInstance().getReference("Cart");

        gioHangRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gioHangList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                double subtotal = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GioHang gioHangItem = snapshot.getValue(GioHang.class);
                    gioHangList.add(gioHangItem);

                    Integer soLuong = gioHangItem.getNumber_pr();
                    if (soLuong != null) {
                        int soLuongInt = soLuong.intValue();
                        double giaTien = gioHangItem.getPrice_pr();
                        double itemTotal = soLuongInt * giaTien;
                        subtotal += itemTotal;
                    } else {
                        // Xử lý khi giá trị số lượng là null
                        Toast.makeText(getContext(), "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                adapter.notifyDataSetChanged(); // Cập nhật RecyclerView khi có thay đổi
                double totaltax = subtotal * 0.1;
                // Cập nhật giá trị subtotal và tổng
                tv_subtotal.setText(String.valueOf(subtotal) + "đ");
                tv_delivery.setText(String.valueOf(delivery) + "đ");
                tv_tax.setText(String.valueOf(totaltax) + "đ");

                double total = subtotal + delivery + totaltax;
                tv_total.setText(String.valueOf(total) + "đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return 0; // Giá trị trả về tùy thuộc vào yêu cầu của bạn, bạn có thể thay đổi thành giá trị khác nếu cần.
    }

    private void clearCart() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
        cartRef.removeValue(); // Xóa toàn bộ dữ liệu trong nút "Cart" trên Firebase Realtime Database
    }



}


