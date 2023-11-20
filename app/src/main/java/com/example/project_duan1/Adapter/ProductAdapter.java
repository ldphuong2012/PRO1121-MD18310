package com.example.project_duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Detail.DetailProduct;
import com.example.project_duan1.R;
import com.example.project_duan1.Update.UpdateProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<MyViewHolder>{
    Context context;
    List<Product> productList;
    String imageUrl="";
    String key="";

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_pr,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(productList.get(position).getImage()).into(holder.recImage);
        holder.recName.setText(productList.get(position).getName());
        holder.recNumber.setText(productList.get(position).getNumber());
        holder.recPrice.setText(productList.get(position).getPrice());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailProduct.class);
                intent.putExtra("Image",productList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Name",productList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Price",productList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("TypeProduct",productList.get(holder.getAdapterPosition()).getTypeProduct());
                intent.putExtra("Number",productList.get(holder.getAdapterPosition()).getNumber());
                intent.putExtra("Description",productList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Key",productList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);

            }
        });
        holder.img_delete_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Lấy thông tin từ mục được chọn
                Product selectedProduct = productList.get(holder.getAdapterPosition());
                imageUrl = selectedProduct.getImage();
                key = selectedProduct.getKey();

                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                                        // Xoá mục khỏi danh sách và thông báo cho Adapter
                                        int position = holder.getAdapterPosition();
                                        if (position != RecyclerView.NO_POSITION && position < productList.size()) {
                                            // Xoá chỉ khi vị trí hợp lệ
                                            productList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, productList.size());
                                        } else {
                                            // Xử lý khi vị trí không hợp lệ
                                            Log.e("DeleteData", "Invalid position for removal: " + position);
                                        }

                                        notifyItemRemoved(holder.getAdapterPosition());
                                        notifyItemRangeChanged(holder.getAdapterPosition(), productList.size());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("DeleteData", "Error deleting data from Realtime Database", e);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("DeleteData", "Error deleting data from Storage", e);
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

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.img_edit_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.img_edit_pr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UpdateProduct.class);

                        // Lấy thông tin từ mục được chọn
                        Product selectedProduct = productList.get(holder.getAdapterPosition());

                        intent.putExtra("Name", selectedProduct.getName());
                        intent.putExtra("Price", selectedProduct.getPrice());
                        intent.putExtra("TypeProduct", selectedProduct.getTypeProduct());
                        intent.putExtra("Number", selectedProduct.getNumber());
                        intent.putExtra("Description", selectedProduct.getDescription());
                        intent.putExtra("Image", selectedProduct.getImage());
                        intent.putExtra("Key", selectedProduct.getKey());

                        context.startActivity(intent);
                    }
                });

            }
        });




    }



    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void  searchProduct(ArrayList<Product> searchList){
        productList=searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recName,recNumber,recPrice;
    CardView recCard;
    ImageView img_delete_pr,img_edit_pr;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage=itemView.findViewById(R.id.recImage);
        recName=itemView.findViewById(R.id.recName);
        recNumber=itemView.findViewById(R.id.recNumber);
        recPrice=itemView.findViewById(R.id.recPrice);
        recCard=itemView.findViewById(R.id.recCard);
        img_delete_pr=itemView.findViewById(R.id.img_delete_pr);
        img_edit_pr=itemView.findViewById(R.id.img_edit_pr);

    }
}


