package com.example.project_duan1.Fragment_Bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.project_duan1.Adapter.CartAdapter;
import com.example.project_duan1.DTO.Favourite;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.Manager.ChangeNumberListener;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        rec_Cart= view.findViewById(R.id.recycler_Cart);
        tv_subtotal= view.findViewById(R.id.tv_subtotal_d);
        tv_delivery= view.findViewById(R.id.tv_delivery_d);
        tv_tax= view.findViewById(R.id.tv_totaltax_d);
        tv_total= view.findViewById(R.id.tv_total_d);
        scrollView= view.findViewById(R.id.scoll_view_cart);
        gioHangList= new ArrayList<>();
        adapter= new CartAdapter(gioHangList,getContext());
        rec_Cart.setAdapter(adapter);
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
    }



    }










