package com.example.project_duan1.Detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_duan1.Fragment_Nav.QLSanPhamFragment;
import com.example.project_duan1.MainActivity;
import com.example.project_duan1.R;
import com.example.project_duan1.Update.UpdateProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailProduct extends AppCompatActivity {
    TextView detailName, detailTypePr, detailPrice, detailDes, detailNumber;
    ImageView detailImg;

    String key = "";
    String imageUrl = "";
/*    ImageView img_delete_pr,img_edit_pr;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        detailName = findViewById(R.id.detailName);
        detailTypePr = findViewById(R.id.detailTypePr);
        detailPrice = findViewById(R.id.detailPrice);
        detailDes = findViewById(R.id.detailDes);
        detailNumber = findViewById(R.id.detailNumber);
        detailImg = findViewById(R.id.detailImg);
    /*    img_delete_pr = findViewById(R.id.img_delete_pr);
        img_edit_pr = findViewById(R.id.img_edit_pr);*/

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailName.setText(bundle.getString("Name"));
            detailTypePr.setText(bundle.getString("TypeProduct"));
            detailPrice.setText(bundle.getString("Price"));
            detailDes.setText(bundle.getString("Description"));
            detailNumber.setText(bundle.getString("Number"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImg);

        }
      /*  img_delete_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                AlertDialog.Builder builder= new AlertDialog.Builder(DetailProduct.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(DetailProduct.this, "Deleted", Toast.LENGTH_SHORT).show();
                               FragmentManager fragmentManager= getSupportFragmentManager();
                               fragmentManager.popBackStack();
                                finish();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }



        });
        img_edit_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent= new Intent(DetailProduct.this, UpdateProduct.class)
                                .putExtra("Name",detailName.getText().toString())
                                .putExtra("Price",detailPrice.getText().toString())
                                .putExtra("TypeProduct",detailTypePr.getText().toString())
                                .putExtra("Number",detailNumber.getText().toString())
                                .putExtra("Description",detailDes.getText().toString())
                                .putExtra("Image",imageUrl)
                                .putExtra("Key",key);
                        startActivity(intent);
                    }


        });*/
    }
}