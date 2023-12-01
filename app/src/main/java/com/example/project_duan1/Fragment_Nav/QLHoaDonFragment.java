package com.example.project_duan1.Fragment_Nav;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_duan1.Adapter.HoaDonAdapter;
import com.example.project_duan1.Adapter.SanPhamDaChonAdapter;
import com.example.project_duan1.Model.GioHangHoaDon;
import com.example.project_duan1.Model.HoaDon;
import com.example.project_duan1.Model.NhanVien;
import com.example.project_duan1.R;
import com.example.project_duan1.add_hoadon;
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
 * Use the {@link QLHoaDonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLHoaDonFragment extends Fragment {
    private RecyclerView recyclerhoadon;

    private HoaDonAdapter hoaDonAdapter;
    private ArrayList<HoaDon> hoaDonMoi;

    FloatingActionButton floataddhoadon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLHoaDonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLHoaDonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLHoaDonFragment newInstance(String param1, String param2) {
        QLHoaDonFragment fragment = new QLHoaDonFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_hoa_don, container, false);
        recyclerhoadon = view.findViewById(R.id.recyclerhoadon);
        floataddhoadon = view.findViewById(R.id.hoadonfloatadd);
        EditText edttimkiemhoadon = view.findViewById(R.id.timkiemhoadon);

        hoaDonMoi = new ArrayList<>();

        loatData();
        getListgioHang();
        floataddhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getContext(), add_hoadon.class);
                startActivity(intent);
            }
        });

        edttimkiemhoadon.clearFocus();
        edttimkiemhoadon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchText = charSequence.toString().toLowerCase();
                hoaDonAdapter.getFilter().filter(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    private void loatData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerhoadon.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL );
        recyclerhoadon.addItemDecoration(dividerItemDecoration);

        hoaDonAdapter = new HoaDonAdapter(hoaDonMoi, getContext(), new HoaDonAdapter.IclickListenner() {
            @Override
            public void onClickDeleteItem1(HoaDon hoaDon) {
                onClickDeletedatta(hoaDon);
            }
        });
        recyclerhoadon.setAdapter(hoaDonAdapter);
    }

    private void getListgioHang() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("HoaDon");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hoaDonMoi.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    HoaDon hoaDon = snapshot1.getValue(HoaDon.class);
                    hoaDonMoi.add(hoaDon);
                }
                hoaDonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Khong tải được dữ liệu từ firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void onClickDeletedatta(HoaDon hoaDon){
        new AlertDialog.Builder(getContext())
                .setTitle("Cảnh báo")
                .setMessage("Bạn có chắc chắn muốn xóa hóa đơn này không")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("HoaDon");

                        myRef.child(String.valueOf(hoaDon.getMahoadon())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Hủy",null)
                .show();
    }


}