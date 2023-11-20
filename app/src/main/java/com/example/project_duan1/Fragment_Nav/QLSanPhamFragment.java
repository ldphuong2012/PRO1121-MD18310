package com.example.project_duan1.Fragment_Nav;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_duan1.Adapter.ProductAdapter;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.R;
import com.example.project_duan1.Upload.UploadProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLSanPhamFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Product> productList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    ProductAdapter productAdapter;
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public QLSanPhamFragment() {
    }


    public static QLSanPhamFragment newInstance() {
        QLSanPhamFragment fragment = new QLSanPhamFragment();

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
        return inflater.inflate(R.layout.fragment_q_l_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycleView);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(context, productList);
        recyclerView.setAdapter(productAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Product dataclass = itemSnapshot.getValue(Product.class);
                    dataclass.setKey(itemSnapshot.getKey());
                    productList.add(dataclass);
                }
                productAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadProduct.class);
                startActivity(intent);
            }
        });


    }

    public void searchList(String text) {
        ArrayList<Product> searchList = new ArrayList<>();
        for (Product dataclass : productList) {
            if (dataclass.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataclass);
            }
        }
        productAdapter.searchProduct(searchList);
    }




    }
