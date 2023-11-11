package com.example.project_duan1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project_duan1.Fragment_Bottom.CartFragment;

import com.example.project_duan1.Fragment_Bottom.AccountFragment;
import com.example.project_duan1.Fragment_Bottom.ExploreFragment;
import com.example.project_duan1.Fragment_Bottom.FavoriteFragment;
import com.example.project_duan1.Fragment_Bottom.HomeFragment;
import com.example.project_duan1.Fragment_Nav.QLHoaDonFragment;
import com.example.project_duan1.Fragment_Nav.QLKhachHangFragment;
import com.example.project_duan1.Fragment_Nav.QLNhanVienFragment;
import com.example.project_duan1.Fragment_Nav.QLSanPhamFragment;
import com.example.project_duan1.Fragment_Nav.ThongKeDoanhThuFragment;
import com.example.project_duan1.Fragment_Nav.ThongKeMatHangFragment;
import com.example.project_duan1.Fragment_Nav.XNDonHangFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_duan1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



DrawerLayout drawerLayout;
BottomNavigationView bottomNavigationView;
    NavigationView nav_view;

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        drawerLayout= findViewById(R.id.drawer_layout);
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
       nav_view= findViewById(R.id.nav_view);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(this);
         bottomNavigationView.setBackground(null);
         bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.search) {
                    replaceFragment(new ExploreFragment());
                    return true;
                }
                else if (item.getItemId() == R.id.cart) {
                    replaceFragment(new CartFragment());
                    return true;
                }else if (item.getItemId() == R.id.favorite) {
                    replaceFragment(new FavoriteFragment());
                    return true;
                } else if (item.getItemId() == R.id.account) {
                    replaceFragment(new AccountFragment());
                    return true;
                }

                return false;
            }
        });

          fragmentManager=getSupportFragmentManager();
          replaceFragment(new HomeFragment());

    }






    private  void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_qlysp) {
            replaceFragment(new QLSanPhamFragment());
        } else if (item.getItemId() == R.id.nav_hoadon) {
            replaceFragment(new QLHoaDonFragment());
        } else if (item.getItemId() == R.id.nav_khachhang) {
            replaceFragment(new QLKhachHangFragment());
        } else if (item.getItemId() ==  R.id.nav_nhanvien) {
            replaceFragment(new QLNhanVienFragment());
        } else if (item.getItemId() == R.id.nav_donhang) {
            replaceFragment(new XNDonHangFragment());
        } else if (item.getItemId() == R.id.nav_doanhthu) {
            replaceFragment(new ThongKeDoanhThuFragment());
        } else if (item.getItemId() == R.id.nav_banchay) {
            replaceFragment(new ThongKeMatHangFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
