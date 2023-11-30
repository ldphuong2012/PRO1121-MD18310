package com.example.project_duan1.Fragment_Bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project_duan1.Adapter.CartAdapter;
import com.example.project_duan1.Adapter.FavouriteAdapter;
import com.example.project_duan1.DTO.Favourite;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    RecyclerView rec_Favourite;
    FavouriteAdapter adapter;
    List<Favourite> favouriteList;


    public FavoriteFragment() {
        // Required empty public constructor


    }


    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();

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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rec_Favourite = view.findViewById(R.id.rec_favourite);
        favouriteList = new ArrayList<>();

        // Check if the context is null before creating the adapter

            adapter = new FavouriteAdapter(favouriteList, getContext());
            rec_Favourite.setAdapter(adapter);


        DatabaseReference favouriteRef = FirebaseDatabase.getInstance().getReference("Favourites");

        favouriteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favouriteList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Favourite gioHangItem = snapshot.getValue(Favourite.class);
                    favouriteList.add(gioHangItem);
                }

                adapter.notifyDataSetChanged(); // Cập nhật RecyclerView khi có thay đổi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}
