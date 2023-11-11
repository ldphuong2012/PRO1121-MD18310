package com.example.project_duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView tv_dangkyngay;
    private FirebaseAuth mAuth;
    private TextInputEditText tip_email_login,tip_password_login;
    private Button btn_login;
    private CheckBox cb_luumk;
    Context context= LoginActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_dangkyngay= findViewById(R.id.tv_dangkyngay);

        tip_email_login= findViewById(R.id.tip_email_login);
        tip_password_login= findViewById(R.id.tip_password_login);
        btn_login=findViewById(R.id.btn_login);
        cb_luumk= findViewById(R.id.cb_luupass);
        mAuth = FirebaseAuth.getInstance();
        tv_dangkyngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        cb_luumk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("password", tip_password_login.getText().toString());
                    editor.apply();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("password");
                    editor.apply();
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=tip_email_login.getText().toString();
                String password = tip_password_login.getText().toString();
                boolean hasError = Validate();

                if (!hasError) {
                    DangNhap(email, password);
                }

            }
        });

    }
    private void DangNhap(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(context, "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(context,MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(context, "Tài khoản mật khẩu chưa có",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private boolean Validate() {
        String email = tip_email_login.getText().toString();
        String password = tip_password_login.getText().toString();
        boolean hasError = false;

        if (email.equals("")) {
            Toast.makeText(context, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            hasError = true;
        }else if (email.equals("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+")){
            Toast.makeText(context, "Bạn nhập sai định dạng email", Toast.LENGTH_SHORT).show();
        }
        else if (password.equals("")) {
            Toast.makeText(context, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            hasError = true;
        }

        return hasError;
    }
}