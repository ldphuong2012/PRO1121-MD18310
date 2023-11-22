package com.example.project_duan1.Upload;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class UploadProduct extends AppCompatActivity {
    ImageView uploadImg;
    Button btn_add;
    EditText uploadName,uploadPrice,uploadTypePr,uploadNumber,uploadDes;
    String imageURL;
    Uri uri;
    TextView tv_cong_pr,tv_tru_pr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        uploadImg = findViewById(R.id.uploadImg);
        uploadName = findViewById(R.id.uploadName);
        uploadPrice = findViewById(R.id.uploadPrice);
        uploadTypePr = findViewById(R.id.uploadTypePr);
        uploadNumber = findViewById(R.id.uploadNumber);
        uploadDes = findViewById(R.id.uploadDes);
        tv_tru_pr = findViewById(R.id.tv_tru_upload);
        tv_tru_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curentText=uploadNumber.getText().toString();
                int curentValue=0;
                if (!curentText.isEmpty()){
                    curentValue = Integer.parseInt(curentText);

                }
                int newValue = Math.max(0, curentValue - 1);
                uploadNumber.setText(String.valueOf(newValue));

            }
        });
        tv_cong_pr= findViewById(R.id.tv_cong_upload);
        tv_cong_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curentText=uploadNumber.getText().toString();
                int curentValue=0;
                if (!curentText.isEmpty()){
                    curentValue = Integer.parseInt(curentText);

                }
                int newValue = curentValue+1;
                uploadNumber.setText(String.valueOf(newValue));
            }
        });
        uploadDes = findViewById(R.id.uploadDes);
        btn_add = findViewById(R.id.btn_add);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImg.setImageURI(uri);
                        }else {
                            Toast.makeText(UploadProduct.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent(Intent.ACTION_PICK);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    saveData();
                } else {
                    Toast.makeText(UploadProduct.this, "Vui lòng chọn một hình ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Product Images")
                .child(Objects.requireNonNull(uri.getLastPathSegment()));
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadProduct.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri urlImage = task.getResult();
                            imageURL = urlImage.toString();
                            uploadData();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(UploadProduct.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadProduct.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }
        public void uploadData(){
        String name = uploadName.getText().toString();
        String price = uploadPrice.getText().toString();
        String typePR = uploadTypePr.getText().toString();
        String number = uploadNumber.getText().toString();
        String des = uploadDes.getText().toString();
        if (uri == null){
            Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(typePR)){
            Toast.makeText(this, "Vui lòng nhập loại sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Vui lòng nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(des)){
            Toast.makeText(this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
        }else {

            Product product = new Product(imageURL, name, price, typePR, number, des);
            FirebaseDatabase.getInstance().getReference("Products").child(name).setValue(product)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UploadProduct.this, "Saved", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.e("UploadProduct", "Failed to save data: " + task.getException().getMessage());
                                Toast.makeText(UploadProduct.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadProduct.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}