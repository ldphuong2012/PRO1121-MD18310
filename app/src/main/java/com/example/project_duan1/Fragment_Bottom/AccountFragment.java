package com.example.project_duan1.Fragment_Bottom;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.DAO.AccountDAO;
import com.example.project_duan1.LoginActivity;
import com.example.project_duan1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    Button btn_Dangxuat;
    TextView tennguoidung,tv_doimatkhau;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_Dangxuat=view.findViewById(R.id.btn_dangxuat);
        tennguoidung= view.findViewById(R.id.tennguoidung);
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("Accounts",MODE_PRIVATE);
        String hoten= sharedPreferences.getString("name","");
        tennguoidung.setText("Xin chào, "+hoten);
        tv_doimatkhau= view.findViewById(R.id.tv_doimatkhau);
        tv_doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialogDoiMk();
            }
        });
        btn_Dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void showDialogDoiMk() {
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        LayoutInflater inflater= getLayoutInflater();
        View view=inflater.inflate(R.layout.activity_change_password,null);
        EditText ed_oldpass= view.findViewById(R.id.tip_passwordold);
        EditText ed_newpass= view.findViewById(R.id.tip_passwordnew);
        EditText ed_repass= view.findViewById(R.id.tip_repass);
        builder.setView(view);
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String oldpass= ed_oldpass.getText().toString();
                String newpass= ed_newpass.getText().toString();
                String repass= ed_repass.getText().toString();
                if (newpass.equals(repass)){
                    SharedPreferences sharedPreferences= getContext().getSharedPreferences("Accounts",MODE_PRIVATE);
                    String username= sharedPreferences.getString("username","");
                    AccountDAO accountDAO= new AccountDAO(getContext());
                    boolean check=accountDAO.capnhatmk(username,oldpass,newpass);
                    if (check){
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getContext(),LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(), "Nhập mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();

    }
}