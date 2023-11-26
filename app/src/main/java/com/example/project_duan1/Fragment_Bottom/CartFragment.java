package com.example.project_duan1.Fragment_Bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_duan1.Adapter.CartAdapter;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    RecyclerView rec_Cart;
    CartAdapter adapter;
    List<GioHang> manggiohang;


    public CartFragment() {
        manggiohang = new ArrayList<>();

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
        if (manggiohang == null) {
            manggiohang = new ArrayList<>();
        }

        adapter = new CartAdapter(manggiohang, getContext());
        rec_Cart.setAdapter(adapter);
    }
}