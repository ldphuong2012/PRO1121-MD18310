package com.example.project_duan1.Fragment_Bottom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.project_duan1.Adapter.ProductAdapter;
import com.example.project_duan1.Adapter.ProductAdapter_Main;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    ProductAdapter_Main adapterMain;
    List<Product> productList;
  androidx.appcompat.widget.SearchView searchView;



    public HomeFragment() {

    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFlipper= view.findViewById(R.id.view_flipper);
        ActionViewFlipper();
        recyclerViewMain= view.findViewById(R.id.rcv_allproducts);
        searchView= view. findViewById(R.id.searchViewHome);
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


      Loaddata();



    }
    private void ActionViewFlipper(){

        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://img.pikbest.com/backgrounds/20210806/color-gravity-farm-supermarket-organic-food-banner_6080036.jpg!sw800");
        mangquangcao.add("https://png.pngtree.com/png-clipart/20210808/original/pngtree-cartoon-minimalistic-line-farm-organic-food-web-banner-png-image_6621471.jpg");
        mangquangcao.add("https://png.pngtree.com/png-clipart/20210808/original/pngtree-farm-supermarket-organic-vegetables-web-banner-png-image_6621474.jpg");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getContext());
            Glide.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    private void Loaddata() {
        productList = new ArrayList<>();
        adapterMain = new ProductAdapter_Main(getContext(), productList);
        recyclerViewMain.setAdapter(adapterMain);

        // Khởi tạo DatabaseReference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        // Thực hiện truy vấn
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa toàn bộ dữ
                // Xử lý dữ liệu sau khi nhận được từ Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert dữ liệu từ dataSnapshot thành đối tượng Product
                    Product product = snapshot.getValue(Product.class);

                    // Thêm sản phẩm vào danh sách
                    productList.add(product);
                }
                adapterMain.notifyDataSetChanged();
                // Cập nhật RecyclerView khi dữ liệu thay đổi

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn Firebase
            }
        });
    }
    private void searchList(String text){
            ArrayList<Product> searchList = new ArrayList<>();
            for (Product dataclass : productList) {
                if (dataclass.getName().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(dataclass);
                }
            }
            adapterMain.searchProduct(searchList);
        }
    }


