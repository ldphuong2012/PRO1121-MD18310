package com.example.project_duan1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project_duan1.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupActivity extends AppCompatActivity {
    Button btn_back;
    private FirebaseAuth mAuth;
    Context context= SignupActivity.this;
    private TextInputEditText tip_email_signup,tip_password_signup,tip_tennguoidung,tip_re_password_signup;
    private Button btn_signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     setContentView(R.layout.activity_signup);
     btn_back= findViewById(R.id.btn_backlogin);
        tip_email_signup= findViewById(R.id.tip_email_signup);
        tip_password_signup= findViewById(R.id.tip_password_signup);
        tip_tennguoidung= findViewById(R.id.tip_tennguoidung);
        tip_re_password_signup= findViewById(R.id.tip_re_password_signup);
        btn_signup=findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

        btn_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(SignupActivity.this, LoginActivity.class));
         }
     });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=tip_email_signup.getText().toString();
                String password = tip_password_signup.getText().toString();
                boolean hasError = Validate();

                if (!hasError) {
                    DangKy(email,password);
                }


            }
        });
    }
    private void DangKy(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(context,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean Validate() {
        String tennguoidung= tip_tennguoidung.getText().toString();
        String re_pass= tip_re_password_signup.getText().toString();

        String email = tip_email_signup.getText().toString();
        String password = tip_password_signup.getText().toString();
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
        else if (tennguoidung.equals("")) {
            Toast.makeText(context, "Vui lòng nhập tên người dùng", Toast.LENGTH_SHORT).show();
            hasError = true;
        }
        else if (re_pass.equals("")) {
            Toast.makeText(context, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            hasError = true;
        }


        return hasError;
    }
}