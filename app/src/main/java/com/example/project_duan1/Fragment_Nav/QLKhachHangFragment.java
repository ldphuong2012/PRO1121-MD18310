package com.example.project_duan1.Fragment_Nav;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.Adapter.KhachHang_Adapter;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Model.Khachhang;
import com.example.project_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QLKhachHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLKhachHangFragment extends Fragment {
    SearchView search_kh;

    RecyclerView recyclerview_qlkh;
    FloatingActionButton add_khachhang;

    private KhachHang_Adapter khachHangAdapter;
    private ArrayList<Khachhang> mListKhachhang;

    TextView txt_makhachhang, txt_tenkhachhang, txt_diachi, txt_sodienthoai;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLKhachHangFragment() {
        // Required empty public constructor
    }


    public static QLKhachHangFragment newInstance() {
        QLKhachHangFragment fragment = new QLKhachHangFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_khach_hang, container, false);
        recyclerview_qlkh = view.findViewById(R.id.recyclerview_qlkh);
        add_khachhang = view.findViewById(R.id.add_khachhang);
         search_kh = view.findViewById(R.id.search_kh);
        search_kh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        // Initialize the adapter
        mListKhachhang = new ArrayList<>();
        khachHangAdapter = new KhachHang_Adapter(mListKhachhang, new KhachHang_Adapter.IclickListenner() {
            @Override
            public void onclickUpdateItem(Khachhang khachhang) {
                openDialogUpdate(khachhang);
            }

            @Override
            public void onClickDeleteItem(Khachhang khachhang) {
                onClickDeletedatta(khachhang);
            }
            //... (your click listeners)
        });

        // Set layout manager and adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_qlkh.setLayoutManager(linearLayoutManager);
        recyclerview_qlkh.setAdapter(khachHangAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerview_qlkh.addItemDecoration(dividerItemDecoration);

        // Load data and set click listener
        loaddata();
        add_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        search_kh.clearFocus();



        // Fetch data from Firebase
        getListKhachHangdatabase();

        return view;
    }
    public void loaddata(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview_qlkh.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerview_qlkh.addItemDecoration(dividerItemDecoration);

        mListKhachhang = new ArrayList<>();
        khachHangAdapter = new KhachHang_Adapter(mListKhachhang, new KhachHang_Adapter.IclickListenner() {
            @Override
            public void onclickUpdateItem(Khachhang khachhang) {
                openDialogUpdate(khachhang);
            }

            @Override
            public void onClickDeleteItem(Khachhang khachhang) {
                onClickDeletedatta(khachhang);
            }
        });
        recyclerview_qlkh.setAdapter(khachHangAdapter);

    }
    Dialog dialog;
    private void showdialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_khachhang);

        txt_makhachhang = dialog.findViewById(R.id.edt_makh_add);
        txt_tenkhachhang = dialog.findViewById(R.id.edt_tenkh_add);
        txt_diachi = dialog.findViewById(R.id.edt_diaChi_add);
        txt_sodienthoai = dialog.findViewById(R.id.edt_sdt_add);
        Button btnthem = dialog.findViewById(R.id.btn_add_kh);
        Button btnhuy = dialog.findViewById(R.id.btn_huy);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makh = txt_makhachhang.getText().toString().trim();
                String tenkh = txt_tenkhachhang.getText().toString().trim();
                String diachi = txt_diachi.getText().toString().trim();
                String sdt = txt_sodienthoai.getText().toString().trim();

                // Kiểm tra nếu bất kỳ trường nào trống
                if (makh.isEmpty() || tenkh.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
                    // Hiển thị thông báo lỗi khi có trường để trống
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (sdt.length() != 10 || !sdt.matches("[0-9]+")) {
                    // Kiểm tra số điện thoại có đủ 10 chữ số và không chứa ký tự đặc biệt
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    // Dữ liệu hợp lệ, thêm khách hàng
                    Khachhang khachhang = new Khachhang(makh, tenkh, diachi, sdt);
                    onClickAddUser(khachhang);
                    dialog.dismiss(); // Đóng hộp thoại sau khi thêm thành công
                }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void getListKhachHangdatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Khachhang");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Khachhang khachhang = snapshot.getValue(Khachhang.class);
                if (khachhang != null){
                    mListKhachhang.add(khachhang);
                    khachHangAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Khachhang khachhang = snapshot.getValue(Khachhang.class);
                if (khachhang == null || mListKhachhang == null || mListKhachhang.isEmpty()){
                    return;
                }
                for (int i = 0 ; i < mListKhachhang.size(); i++){
                    if (khachhang.getMakh() == mListKhachhang.get(i).getMakh()){
                        mListKhachhang.set(i, khachhang);
                        break;
                    }
                }
                khachHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Khachhang khachhang = snapshot.getValue(Khachhang.class);
                if (khachhang == null || mListKhachhang == null || mListKhachhang.isEmpty()){
                    return;
                }
                for (int i = 0; i < mListKhachhang.size(); i++){
                    if (khachhang.getMakh() == mListKhachhang.get(i).getMakh()){
                        mListKhachhang.remove(mListKhachhang.get(i));
                        break;
                    }
                }
                khachHangAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void onClickAddUser(Khachhang khachhang){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Khachhang");

        String pathOjbject = String.valueOf(khachhang.getMakh());

        myRef.child(pathOjbject).setValue(khachhang, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "Add thành công khách hàng ", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();

    }
    private void openDialogUpdate(Khachhang khachhang){


        final Dialog dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_update_khachhang);
        Window window = dialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);

        txt_makhachhang = dialog1.findViewById(R.id.edt_makh_update);
        txt_tenkhachhang = dialog1.findViewById(R.id.edt_tenkh_update);
        txt_diachi = dialog1.findViewById(R.id.edt_daiChi_update);
        txt_sodienthoai = dialog1.findViewById(R.id.edt_sdt_update);
        Button btnupdate = dialog1.findViewById(R.id.btn_updatekh);
        Button btnhuy = dialog1.findViewById(R.id.btn_huy);

        txt_makhachhang.setText(khachhang.getMakh()+"");
        txt_tenkhachhang.setText(khachhang.getHoTen());
        txt_diachi.setText(khachhang.getDaiChi());
        txt_sodienthoai.setText(khachhang.getSoDienThoaiKH());


        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Khachhang");

                String new_makh = txt_makhachhang.getText()+"".toString().trim();
                String new_tenkh = txt_tenkhachhang.getText().toString().trim();
                String new_diachi = txt_diachi.getText().toString().trim();
                String new_sdt = txt_sodienthoai.getText().toString().trim();

                if (new_makh.isEmpty() || new_tenkh.isEmpty() || new_diachi.isEmpty() || new_sdt.isEmpty()) {
                    // Hiển thị thông báo lỗi khi có trường để trống
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (new_sdt.length() != 10 || !new_sdt.matches("[0-9]+")) {
                    // Kiểm tra số điện thoại có đủ 10 chữ số và không chứa ký tự đặc biệt
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    // Dữ liệu hợp lệ, gán giá trị vào đối tượng khachhang
                    khachhang.setMakh(Integer.parseInt(new_makh));
                    khachhang.setHoTen(new_tenkh);
                    khachhang.setDaiChi(new_diachi);
                    khachhang.setSoDienThoaiKH(new_sdt);
                }

                myRef.child(String.valueOf(khachhang.getMakh())).updateChildren(khachhang.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                });
            }
        });


        dialog1.show();
    }
    private void onClickDeletedatta(Khachhang khachhang){
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc chắn muốn xóa người này không")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Khachhang");

                        myRef.child(String.valueOf(khachhang.getMakh())).removeValue(new DatabaseReference.CompletionListener() {
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
    public void searchList(String text) {
        ArrayList<Khachhang> searchList = new ArrayList<>();
        for (Khachhang dataclass : mListKhachhang) {
            String soDienThoaiKH = dataclass.getSoDienThoaiKH();
            if (soDienThoaiKH != null && soDienThoaiKH.toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataclass);
            }
        }
        khachHangAdapter.searchProduct(searchList);
    }

}