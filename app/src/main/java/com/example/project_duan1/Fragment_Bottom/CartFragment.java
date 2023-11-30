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
        calculatorCart();



    }

    private void fetchCartDetails() {
        // Assuming you have a reference to your Firebase Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Your_Cart_Node");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gioHangList.clear(); // Clear the list before adding new data

                // Iterate through the dataSnapshot to get cart items
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GioHang gioHang = snapshot.getValue(GioHang.class);
                    gioHangList.add(gioHang);
                }

                // Update RecyclerView and calculate cart
                updateCartUI();
                double cartTotal = calculatorCart();
                // Now, you can use cartTotal as needed (e.g., display in TextView)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private void updateCartUI() {
        // Set up your RecyclerView and adapter here
        // ...
        // For example:
        // adapter = new CartAdapter(getContext(), gioHangList);
        // rec_Cart.setAdapter(adapter);
    }

    private double calculatorCart() {
        double fee = 0;
        for (int i = 0; i < gioHangList.size(); i++) {
            fee = fee + (gioHangList.get(i).getPrice_pr() *gioHangList.get(i).getNumber_pr());
        }

        // Update TextViews with calculated values
        tv_subtotal.setText(String.valueOf(fee));
        // You can calculate and update other values (delivery, tax, total) here

        return fee;
    }
    public void minusNumberItem(List<GioHang> gioHangs, int position, ChangeNumberListener changeNumberListener){
        if (gioHangs.get(position).getNumber_pr()==1){
            gioHangs.remove(position);
        }
        else {
            gioHangs.get(position).setNumber_pr(gioHangs.get(position).getNumber_pr()-1);
        }
    }
}
