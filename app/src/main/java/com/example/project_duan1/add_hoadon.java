package com.example.project_duan1;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.Adapter.SanPhamDaChonAdapter;
import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Fragment_Nav.QLHoaDonFragment;
import com.example.project_duan1.Model.EventBus.TinhTongEvent;
import com.example.project_duan1.Model.GioHangHoaDon;
import com.example.project_duan1.Model.HoaDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class add_hoadon extends AppCompatActivity {

    RecyclerView recyclerthemhoadon;

    private TextView txttongtien;

    SanPhamDaChonAdapter sanPhamDaChonAdapter;
    private ArrayList<GioHangHoaDon> gioHangHoaDonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_them_hoadon);
        txttongtien = findViewById(R.id.txttongtien);
        Button btnthanhtoan = findViewById(R.id.btnthanhtoan);
        Button btnhuy = findViewById(R.id.btnHuythanhtoan);
        Spinner spnkhachhang = findViewById(R.id.spnkhachhang);
        Spinner spnnhanVien = findViewById(R.id.spnnhanvien);
        EditText edttimkiemhd = findViewById(R.id.timkiemhoadon);
        recyclerthemhoadon = findViewById(R.id.recyclerthemhoadon);

        loatdatahoadon();

        getListgioHang();

        tinhtongtien();

        getDataKhachHang(spnkhachhang);
        getDataNhanVien(spnnhanVien);

        edttimkiemhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_hoadon.this,chon_sanpham_hoadon.class);
                startActivity(intent);
            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedKhachHang = spnkhachhang.getSelectedItem().toString();
                String selectedNhanVien = spnnhanVien.getSelectedItem().toString();

                // Tạo một đối tượng HoaDon mới
                HoaDon hoaDon = new HoaDon();
                hoaDon.setTenkhachhang(selectedKhachHang);
                hoaDon.setTennhanvien(selectedNhanVien);
                hoaDon.setGioHangList(gioHangHoaDonList);
                hoaDon.setGiatien(tinhtongtien());

                // Lưu dữ liệu vào Firebase
                saveDataToFirebase(hoaDon);

                // Hiển thị thông báo hoặc chuyển sang màn hình khác (nếu cần)
                Toast.makeText(add_hoadon.this, "Đã thanh toán và lưu dữ liệu!", Toast.LENGTH_SHORT).show();

                // Xóa danh sách giỏ hàng sau khi thanh toán
                gioHangHoaDonList.clear();
                sanPhamDaChonAdapter.notifyDataSetChanged();
                txttongtien.setText("0");

                //Xóa dữ liệu firebase
                clearFirebaseData();
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void clearFirebaseData() {
        // Truy vấn để xóa tất cả các mục trong "ThemSanPham"
        DatabaseReference themSanPhamRef = FirebaseDatabase.getInstance().getReference("ThemSanPham");
        themSanPhamRef.removeValue(); // Xóa toàn bộ nút "products"
    }

    private void saveDataToFirebase(HoaDon hoaDon) {
        // Tạo một đối tượng mới để lưu dữ liệu hóa đơn
        DatabaseReference hoadonRef = FirebaseDatabase.getInstance().getReference("HoaDon");
        String hoaDonId = hoadonRef.push().getKey();

        // Đặt giá trị cho đối tượng hóa đơn
        hoaDon.setMahoadon(hoaDonId);

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String ngayHienTai = dateFormat.format(calendar.getTime());
        String gioHienTai = timeFormat.format(calendar.getTime());
        hoaDon.setNgaytao(ngayHienTai);
        hoaDon.setGiotao(gioHienTai);

        // Lưu dữ liệu hóa đơn vào Firebase
        hoadonRef.child(hoaDonId).setValue(hoaDon);
    }

    private double tinhtongtien() {

        double tongtien = 0;

        for (GioHangHoaDon gioHangHoaDon : gioHangHoaDonList){
            String giaString = gioHangHoaDon.getGiasp();
            if (giaString != null && !giaString.trim().isEmpty()) {
                try {
                    double giasp = Double.parseDouble(giaString.trim());
                    int soluong = gioHangHoaDon.getSoluong();
                    tongtien += giasp * soluong;
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp khi không thể chuyển đổi thành số thực
                    e.printStackTrace();
                }
            }
        }
        return tongtien;
    }

    private void loatdatahoadon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerthemhoadon.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL );
        recyclerthemhoadon.addItemDecoration(dividerItemDecoration);

        gioHangHoaDonList = new ArrayList<>();
        sanPhamDaChonAdapter = new SanPhamDaChonAdapter(gioHangHoaDonList,this);
        recyclerthemhoadon.setAdapter(sanPhamDaChonAdapter);
    }

    private void getListgioHang() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ThemSanPham");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gioHangHoaDonList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    GioHangHoaDon gioHangHoaDon = snapshot1.getValue(GioHangHoaDon.class);
                    gioHangHoaDonList.add(gioHangHoaDon);
                }
                sanPhamDaChonAdapter.notifyDataSetChanged();

                //Cập nhập phương thức tính tông tiền
                double tongtien = tinhtongtien();
                txttongtien.setText(String.valueOf((int) tongtien));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(add_hoadon.this, "Khong tải được dữ liệu từ firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDataKhachHang (Spinner spnkhachhang){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Khachhang");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> khachhangList = new ArrayList<>();
                for (DataSnapshot snapshotChild : snapshot.getChildren()) {
                    // Giả sử bạn có một trường "hoTen" trong nút NhanVien
                    String hoTen = snapshotChild.child("hoTen").getValue(String.class);

                    if (hoTen != null) {
                        khachhangList.add(hoTen);
                    }
                }

                // Tạo ArrayAdapter để hiển thị dữ liệu trên Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(add_hoadon.this, android.R.layout.simple_spinner_item, khachhangList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Set adapter cho Spinner
                spnkhachhang.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(add_hoadon.this, "Không thể đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getDataNhanVien (Spinner spnnhanvien){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("NhanVien");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> nhanvienList = new ArrayList<>();
                for (DataSnapshot snapshotChild : snapshot.getChildren()) {
                    // Giả sử bạn có một trường "ten" trong nút NhanVien
                    String hoTen = snapshotChild.child("ten").getValue(String.class);

                    if (hoTen != null) {
                        nhanvienList.add(hoTen);
                    }
                }

                // Tạo ArrayAdapter để hiển thị dữ liệu trên Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(add_hoadon.this, android.R.layout.simple_spinner_item, nhanvienList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Set adapter cho Spinner
                spnnhanvien.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(add_hoadon.this, "Không thể đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe (sticky = true, threadMode = ThreadMode.MAIN)
    public void eventtinhTien(TinhTongEvent event){
        if (event != null){
            tinhtongtien();
        }
    }
}