package com.example.project_duan1.Update;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.MainActivity;
import com.example.project_duan1.R;
import com.example.project_duan1.Upload.UploadProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UpdateProduct extends AppCompatActivity {
    TextView tv_cong,tv_tru;
    ImageView updateImg;
    Button btn_update;
    EditText updateName,updatePrice,updateTypePr,updateNumber,updateDes;
    String name,price,typePr,number,des;
    String imageUrl;
    String key,oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        updateImg= findViewById(R.id.updateImg);
        updateName=findViewById(R.id.updateName);
        updatePrice=findViewById(R.id.updatePrice);
        updateTypePr=findViewById(R.id.updateTypePr);
        updateNumber=findViewById(R.id.updateNumber);
        updateDes=findViewById(R.id.updateDes);
        tv_cong= findViewById(R.id.tv_cong_update);
        tv_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curentText=updateNumber.getText().toString();
                int curentValue=0;
                if (!curentText.isEmpty()){
                    curentValue = Integer.parseInt(curentText);

                }
                int newValue = curentValue+1;
                updateNumber.setText(String.valueOf(newValue));
            }
        });
        tv_tru= findViewById(R.id.tv_tru_update);
        tv_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curentText=updateNumber.getText().toString();
                int curentValue=0;
                if (!curentText.isEmpty()){
                    curentValue = Integer.parseInt(curentText);

                }
                int newValue = Math.max(0, curentValue - 1);
                updateNumber.setText(String.valueOf(newValue));

            }
        });
        btn_update= findViewById(R.id.btn_update);


        ActivityResultLauncher<Intent> activityResultLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri= data.getData();
                            updateImg.setImageURI(uri);

                        }else {
                            Toast.makeText(UpdateProduct.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            Glide.with(UpdateProduct.this).load(bundle.getString("Image")).into(updateImg);
            updateName.setText(bundle.getString("Name"));
            updatePrice.setText(bundle.getString("Price"));
            updateTypePr.setText(bundle.getString("TypeProduct"));
            updateNumber.setText(bundle.getString("Number"));
            updateDes.setText(bundle.getString("Description"));
            key= bundle.getString("Key");
            oldImageURL = bundle.getString("Image");

        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(key);
        updateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    saveData();

                } else {
                    Toast.makeText(UpdateProduct.this, "Vui lòng chọn một hình ảnh", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void  saveData(){

        if (uri != null) {
            storageReference = FirebaseStorage.getInstance().getReference().child("Product Images")
                    .child(uri.getLastPathSegment());
            // Tiếp tục thực hiện các hành động khác ở đây
        } else {
            // Xử lý khi uri là null, ví dụ: thông báo cho người dùng
            Toast.makeText(UpdateProduct.this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        // Tiếp tục thực hiện các hành động khác ở đây


        AlertDialog.Builder builder= new AlertDialog.Builder(UpdateProduct.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog= builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        String productId = String.valueOf(System.currentTimeMillis());
        name = updateName.getText().toString().trim();
        price = updatePrice.getText().toString().trim();
        typePr = updateTypePr.getText().toString().trim();
        number = (updateNumber.getText().toString().trim());
        des = updateDes.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(typePr)){
            Toast.makeText(this, "Vui lòng nhập loại sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(number)){
            Toast.makeText(this, "Vui lòng nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(des)){
            Toast.makeText(this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
        }else {
            Product dataclass = new Product(productId,imageUrl, name, price, typePr, number, des);


            databaseReference.setValue(dataclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        reference.delete();
                        Toast.makeText(UpdateProduct.this, "Updated", Toast.LENGTH_SHORT).show();


                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateProduct.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}