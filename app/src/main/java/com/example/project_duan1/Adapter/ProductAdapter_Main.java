package com.example.project_duan1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_duan1.DTO.Favourite;
import com.example.project_duan1.DTO.GioHang;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Detail.DetailProduct;
import com.example.project_duan1.Detail.DetailProduct_Main;
import com.example.project_duan1.Manager.CartManager;
import com.example.project_duan1.Manager.FavouriteManager;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class ProductAdapter_Main extends RecyclerView.Adapter<ProductAdapter_Main.MainViewHolder> {
    Context context;
    List<Product> productList;




    public ProductAdapter_Main(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_pr_main,parent,false);
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);
        Glide.with(context).load(productList.get(position).getImage()).into(holder.img_pr_main);
        holder.tv_name_pr_main.setText(productList.get(position).getName());

        holder.tv_price_pr_main.setText("Giá: "+ productList.get(position).getPrice()+"đ");
        holder.recCardMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetailProduct_Main.class);
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


        if (product.isFavourite()) {
            holder.img_favourite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.img_favourite.setImageResource(R.drawable.ic_border_favorite);
        }
        holder.img_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productID = product.getName(); // Lấy ID của sản phẩm

                if (product.isFavourite()) {
                    // Sản phẩm đã được đánh dấu yêu thích

                    Toast.makeText(context, "Đã bỏ yêu thích!", Toast.LENGTH_SHORT).show();


                    // Xóa sản phẩm khỏi danh sách yêu thích trong Firebase
                    deleteFromFirebaseFavourite(productID);
                    product.setFavourite(false);
                    // Cập nhật thông tin sản phẩm trong bảng "Product" trên Firebase
                    updateProductInFirebase(product);
                    holder.img_favourite.setImageResource(R.drawable.ic_border_favorite);

                } else {
                    // Sản phẩm chưa được đánh dấu yêu thích

                    Toast.makeText(context, "Đã thêm vào danh mục yêu thích ♥", Toast.LENGTH_SHORT).show();


                    // Thêm sản phẩm vào danh sách yêu thích trong Firebase
                    addtoFavourite(product);
                    product.setFavourite(true);
                    // Cập nhật thông tin sản phẩm trong bảng "Product" trên Firebase
                    updateProductInFirebase(product);
                    holder.img_favourite.setImageResource(R.drawable.ic_favorite);


                }


            }

        });

        // Các phần code khác...




        holder.img_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
addtoCart(product);
                Toast.makeText(context, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void  searchProduct(ArrayList<Product> searchList){
        productList=searchList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d("zzzz", "Số lương: " +productList.size());
        return productList.size();
    }
    class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView img_pr_main,img_addCart,img_favourite;
        TextView tv_name_pr_main,tv_price_pr_main;
        CardView recCardMain;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pr_main= itemView.findViewById(R.id.img_pr_main);
            tv_name_pr_main= itemView.findViewById(R.id.tv_namePr_main);
            tv_price_pr_main= itemView.findViewById(R.id.tv_pricePr_main);
            img_addCart= itemView.findViewById(R.id.img_addCart);
            img_favourite= itemView.findViewById(R.id.img_favorite);
            recCardMain=itemView.findViewById(R.id.recCardMain);



        }
    }
    private void addtoFavourite(Product product) {
        Favourite objFavourite = new Favourite();
        objFavourite.setImg_pr_favourite(product.getImage());
        objFavourite.setName_pr_favourite(product.getName());
        objFavourite.setPrice_pr_favourite(product.getPrice());

        // Sử dụng CartManager để thêm mục vào giỏ hàng cục bộ
        FavouriteManager.getInstance().addToFavorite(objFavourite);

        // Thêm mục vào giỏ hàng Firebase
        addToFirebaseFavourite(objFavourite);

        // Cập nhật trạng thái sản phẩm thành "isFavorite" = true


    }
    public void updateProductInFirebase(Product product) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        // Truy cập đúng nút con của sản phẩm trong "Product" theo id của sản phẩm
        DatabaseReference productRef = databaseReference.child(product.getID_pr());

        // Cập nhật các thuộc tính của sản phẩm
        productRef.child("isFavorite").setValue(product.isFavourite());
        // Cập nhật các thuộc tính khác của sản phẩm

        // Cập nhật thông tin sản phẩm trong Firebase Realtime Database
        productRef.setValue(product);
    }

    public void addToFirebaseFavourite(Favourite favourite) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Favourites");

      // Tạo một khóa duy nhất cho sản phẩm trong danh sách yêu thích

        String favouriteItemId = databaseReference.push().getKey(); // Tạo một khóa duy nhất cho sản phẩm trong danh sách yêu thích

        favourite.setId_pr_favourite(favouriteItemId);

        // Cung cấp các thông tin khác cho đối tượng Favourite

        // Thêm thông tin sản phẩm vào Firebase Realtime Database
        databaseReference.child(favouriteItemId).setValue(favourite);
    }






    public void deleteFromFirebaseFavourite(String name) {
        String columnName = "name_pr_favourite"; // Tên cột bạn muốn xóa// Giá trị trong cột "name_pr_favourite" bạn muốn xóa

// Xác định nút đích trong Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Favourites");

// Tạo câu truy vấn để xóa mục dựa trên giá trị của cột "name_pr_favourite"
        Query query = databaseReference.orderByChild(columnName).equalTo(name);

// Thực hiện câu truy vấn và xóa mục tương ứng
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue(); // Xóa mục khỏi cơ sở dữ liệu
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi (nếu cần)
            }
        });
    }
    private void addtoCart(Product product) {
        GioHang existingItem = CartManager.getInstance().getCartItemByName(product.getName());
        if (existingItem != null) {
            // Tăng số lượng của sản phẩm đã có trong giỏ hàng
            int currentQuantity = existingItem.getNumber_pr();
            existingItem.setNumber_pr(currentQuantity + 1);
            // Cập nhật giỏ hàng cục bộ
            CartManager.getInstance().updateCartItem(existingItem);
            // Cập nhật giỏ hàng trên Firebase
            updateFirebaseCartItem(existingItem);
        } else {
            GioHang objGiohang = new GioHang();
            objGiohang.setImg_pr(product.getImage());
            objGiohang.setName_pr(product.getName());
            objGiohang.setPrice_pr(Double.parseDouble(product.getPrice()));
            objGiohang.setNumber_pr(1);

            // Thêm vào giỏ hàng cục bộ
            CartManager.getInstance().addToCart(objGiohang);
            // Thêm vào giỏ hàng trên Firebase
            addToFirebaseCart(objGiohang);
        }
        // Cập nhật trạng thái sản phẩm thành "isFavorite" = true
        // ...
    }

    private void addToFirebaseCart(GioHang objGiohang) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");

        String cartItemId = databaseReference.push().getKey();
        objGiohang.setId_pr(cartItemId);

        // Thêm thông tin sản phẩm vào Firebase Realtime Database
        databaseReference.child(cartItemId).setValue(objGiohang);
    }

    private void updateFirebaseCartItem(GioHang objGiohang) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");

        // Sử dụng cartItemId để cập nhật thông tin sản phẩm trong Firebase Realtime Database
        databaseReference.child(objGiohang.getId_pr()).setValue(objGiohang);
    }
}



