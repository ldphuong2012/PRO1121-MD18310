package com.example.project_duan1.Fragment_Nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_duan1.Adapter.TopSanPhamAdapter;
import com.example.project_duan1.Model.TopSanPham;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeMatHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeMatHangFragment extends Fragment {

    private ArrayList<TopSanPham> sanPhams;
    private TopSanPhamAdapter topSanPhamAdapter;

    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewtop;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeMatHangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongKeMatHangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongKeMatHangFragment newInstance(String param1, String param2) {
        ThongKeMatHangFragment fragment = new ThongKeMatHangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_mat_hang, container, false);
        recyclerViewtop = view.findViewById(R.id.recyclerViewTopSanPham);

        //Tạo đường dẫn đến firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhams = new ArrayList<>();
                //Duyệt qua danh sách hóa đơn
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //Lấy danh sách sản phẩm từ mỗi hóa đơn
                    DataSnapshot dssanphamSnapshot = dataSnapshot.child("gioHangList");
                    for (DataSnapshot sanPhamSnapshot : dssanphamSnapshot.getChildren()){
                        TopSanPham sanPham = sanPhamSnapshot.getValue(TopSanPham.class);
                        if (sanPham != null){
                            //Cập nhập sản phẩm vào danh sách
                            updateOrAddSanPham(sanPhams, sanPham);
                        }
                    }
                }
                // Thực hiện thống kê và hiển thị top 10 sản phẩm
                ArrayList<TopSanPham> top10SanPham = thongKeTop10SanPham(sanPhams);
                // Tiếp theo, bạn có thể làm gì đó với danh sách top10SanPham, chẳng hạn hiển thị nó trong RecyclerView

                // Đặt layout manager cho RecyclerView
                recyclerViewtop.setLayoutManager(new LinearLayoutManager(getContext()));

                // Khởi tạo adapter và gán cho RecyclerView
                topSanPhamAdapter = new TopSanPhamAdapter(top10SanPham,getContext());
                recyclerViewtop.setAdapter(topSanPhamAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu từ FireBase", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private ArrayList<TopSanPham> thongKeTop10SanPham(ArrayList<TopSanPham> listSanPham) {
        // Giả sử bạn muốn sắp xếp theo số lượng giảm dần
        Collections.sort(listSanPham, new  Comparator<TopSanPham>() {
            @Override
            public int compare(TopSanPham sanPham1, TopSanPham sanPham2) {
                String soLuong1 = String.valueOf(sanPham1.getSoluong());
                String soLuong2 = String.valueOf(sanPham2.getSoluong());

                return soLuong2.compareTo(soLuong1);
            }
        });

        // Tạo một danh sách mới cho top 10 sản phẩm
        ArrayList<TopSanPham> top10List = new ArrayList<>();

        // Thêm top 10 sản phẩm vào danh sách mới
        for (int i = 0; i < Math.min(4, listSanPham.size()); i++) {
            TopSanPham topSanPham = listSanPham.get(i);
            top10List.add(topSanPham);
        }

        return top10List;
    }
    private void updateOrAddSanPham(ArrayList<TopSanPham> listSanPham, TopSanPham newSanPham) {
        for (TopSanPham sanPham : listSanPham) {
            if (sanPham.getTensp().equals(newSanPham.getTensp())) {
//                if (sanPham.getSoluongban() != null && newSanPham.getSoluongban() != null) {
//                    sanPham.setSoluongban(sanPham.getSoluongban() + newSanPham.getSoluongban());
//                } else {
//                    // Xử lý khi một trong hai giá trị là null
//                }
                // Sản phẩm đã tồn tại, cập nhật số lượng
                sanPham.setSoluong(sanPham.getSoluong() + newSanPham.getSoluong());
                return; // Thoát khỏi phương thức khi đã cập nhật xong
            }
        }

        // Sản phẩm chưa tồn tại, thêm nó vào danh sách
        listSanPham.add(newSanPham);
    }

}