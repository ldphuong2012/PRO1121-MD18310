package com.example.project_duan1.Fragment_Nav;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_duan1.Adapter.NhanVienAdapter;
import com.example.project_duan1.Model.NhanVien;
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
 * Use the {@link QLNhanVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QLNhanVienFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton floatadd;

    private NhanVienAdapter nhanVienAdapter;
    private ArrayList<NhanVien> mListNhanvien;

    EditText edtten,edttuoi,edtchucvu,edtsdt,edtemail;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QLNhanVienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QLNhanVienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QLNhanVienFragment newInstance(String param1, String param2) {
        QLNhanVienFragment fragment = new QLNhanVienFragment();
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
        View view = inflater.inflate(R.layout.fragment_q_l_nhan_vien, container, false);

        recyclerView = view.findViewById(R.id.recyclerthanhvien);
        floatadd = view.findViewById(R.id.thanhvienfloatadd);
        EditText edttimkiem = view.findViewById(R.id.timkiem);


        loatdata();

        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        edttimkiem.clearFocus();
        edttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchText = charSequence.toString().toLowerCase();
                nhanVienAdapter.getFilter().filter(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getListThanhViendatabase();
        return view;
    }

    public void loatdata(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL );
        recyclerView.addItemDecoration(dividerItemDecoration);


        mListNhanvien = new ArrayList<>();
        nhanVienAdapter = new NhanVienAdapter(getContext(),mListNhanvien, new NhanVienAdapter.IclickListenner() {
            @Override
            public void onclickUpdateItem(NhanVien nhanVien) {

                openDialogUpdate(nhanVien);
            }

            @Override
            public void onClickDeleteItem(NhanVien nhanVien) {

                onClickDeletedatta(nhanVien);
            }
        });
        recyclerView.setAdapter(nhanVienAdapter);


    }
    Dialog dialog;
    private void showdialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.item_them_nhan_vien);

        edtten = dialog.findViewById(R.id.extthemhotentv);
        edttuoi = dialog.findViewById(R.id.extthemtuoitv);
        edtchucvu = dialog.findViewById(R.id.edthemchucvutv);
        edtsdt = dialog.findViewById(R.id.edsdttv);
        edtemail = dialog.findViewById(R.id.edemailtv);
        Button btnthem = dialog.findViewById(R.id.btnthemtv);
        Button btnhuy = dialog.findViewById(R.id.btnhuytv);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtten.getText().toString().trim();
                String tuoi = edttuoi.getText().toString().trim();
                String chucvu = edtchucvu.getText().toString().trim();
                String sdt = edtsdt.getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                if (validate() > 0) {
                    NhanVien nhanVien = new NhanVien(ten,tuoi,chucvu,sdt,email);
                    onClickAddUser(nhanVien);
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
    private void onClickAddUser(NhanVien nhanVien){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NhanVien");

        String pathOjbject = String.valueOf(nhanVien.getTen());

        myRef.child(pathOjbject).setValue(nhanVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "Add thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();

    }
    private void getListThanhViendatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NhanVien");

        //cách 1
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Thanhvien thanhvien = dataSnapshot.getValue(Thanhvien.class);
//                    mListThanhvien.add(thanhvien);
//                }
//
//                thanhVienAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Toast.makeText(getContext(), "Khong doc duoc du lieu", Toast.LENGTH_SHORT).show();
//            }
//        });
        //cách 2
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Object value = snapshot.getValue();
                if (value instanceof String) {
                    // Xử lý trường hợp giá trị là chuỗi
                    Log.d("FirebaseData", "Data is a string: " + value);
                } else {
                    // Xử lý trường hợp giá trị là đối tượng Thanhvien
                    NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                    if (nhanVien != null) {
                        // Xử lý đối tượng Thanhvien tại đây
                        mListNhanvien.add(nhanVien);

                        nhanVienAdapter.notifyDataSetChanged();
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                if (nhanVien == null || mListNhanvien == null || mListNhanvien.isEmpty()){
                    return;
                }
                for (int i = 0 ; i < mListNhanvien.size(); i++){
                    if (nhanVien.getTen() == mListNhanvien.get(i).getTen()){
                        mListNhanvien.set(i, nhanVien);
                        break;
                    }
                }
                nhanVienAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                if (nhanVien == null || mListNhanvien == null || mListNhanvien.isEmpty()){
                    return;
                }
                for (int i = 0; i < mListNhanvien.size(); i++){
                    if (nhanVien.getTen() == mListNhanvien.get(i).getTen()){
                        mListNhanvien.remove(mListNhanvien.get(i));
                        break;
                    }
                }
                nhanVienAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogUpdate(NhanVien thanhvien){
        final Dialog dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.item_update_thanhvien);
        Window window = dialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);

        EditText edthoten = dialog1.findViewById(R.id.extupdatehotentv);
        EditText edttuoi = dialog1.findViewById(R.id.extupdatetuoitv);
        EditText edtchucvu = dialog1.findViewById(R.id.extupdatechucvutv);
        EditText edtsdt = dialog1.findViewById(R.id.extupdtesdttv);
        EditText email = dialog1.findViewById(R.id.extupdateemailtv);
        Button btnupdate = dialog1.findViewById(R.id.btncapnhaptv);
        Button btnhuy = dialog1.findViewById(R.id.btnhuycapnhaptv);

        edthoten.setText(thanhvien.getTen());
        edttuoi.setText(thanhvien.getTuoi());
        edtchucvu.setText(thanhvien.getChucvu());
        edtsdt.setText(thanhvien.getSdt());
        email.setText(thanhvien.getEmail());

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
                DatabaseReference myRef = database.getReference("NhanVien");
                String newTen = edthoten.getText().toString().trim();
                String newTuoi = edttuoi.getText().toString().trim();
                String newChucvu = edtchucvu.getText().toString().trim();
                String newsdt = edtsdt.getText().toString().trim();
                String newemail = email.getText().toString().trim();
                thanhvien.setTen(newTen);
                thanhvien.setTuoi(newTuoi);
                thanhvien.setChucvu(newChucvu);
                thanhvien.setSdt(newsdt);
                thanhvien.setEmail(newemail);


                myRef.child(String.valueOf(thanhvien.getTen())).updateChildren(thanhvien.toMap(), new DatabaseReference.CompletionListener() {
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
    private void onClickDeletedatta(NhanVien thanhvien){
        new AlertDialog.Builder(getContext())
                .setTitle("Cảnh báo")
                .setMessage("Bạn có chắc chắn muốn xóa người này không")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("NhanVien");

                        myRef.child(String.valueOf(thanhvien.getTen())).removeValue(new DatabaseReference.CompletionListener() {
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

    public int validate(){
        int check = 1;
        if(edtten.getText().length() == 0 || edttuoi.getText().length()==0 || edtchucvu.getText().length() ==0 || edtsdt.getText().length() ==0 || edtemail.getText().length() ==0){
            Toast.makeText(getContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
            check =-1;
        }
        else if (!edtsdt.getText().toString().matches("\\d{10}")) {
            // Kiểm tra xem chuỗi số điện thoại chỉ chứa các ký tự số và có độ dài là 10
            Toast.makeText(getContext(), "Số điện thoại phải là số và có độ dài là 10", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else if (!edtemail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            Toast.makeText(getContext(), "email phải đúng định dạng", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (nhanVienAdapter != null) {
            nhanVienAdapter.release();
        }
    }
}