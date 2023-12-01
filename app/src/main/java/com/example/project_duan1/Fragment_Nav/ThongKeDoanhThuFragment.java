package com.example.project_duan1.Fragment_Nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_duan1.Model.HoaDon;
import com.example.project_duan1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeDoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeDoanhThuFragment extends Fragment {

    private EditText edtstarDay, edtstopDay;
    private Button btnketqua;
    private TextView txttong;

    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeDoanhThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongKeDoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongKeDoanhThuFragment newInstance(String param1, String param2) {
        ThongKeDoanhThuFragment fragment = new ThongKeDoanhThuFragment();
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
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);

        edtstarDay = view.findViewById(R.id.edtStart);
        edtstopDay = view.findViewById(R.id.edtEnd);
        btnketqua = view.findViewById(R.id.btnthongke);
        txttong = view.findViewById(R.id.txtKetqua);

        databaseReference = FirebaseDatabase.getInstance().getReference("HoaDon");
        setDefaultDates();

        btnketqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performThongKe();
            }
        });


        return view;

    }

    private void setDefaultDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        String currentDate = sdf.format(calendar.getTime());
        edtstarDay.setText(currentDate);
        edtstopDay.setText(currentDate);
    }

    private void performThongKe() {
        String startDate = edtstarDay.getText().toString();
        String endDate = edtstopDay.getText().toString();

        // Retrieve data from Firebase
        databaseReference.orderByChild("ngaytao")
                .startAt(startDate)
                .endAt(endDate)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int totalRevenue = 0;
                        // Duyệt dữ liệu trả về từ firebase
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HoaDon hoaDon = snapshot.getValue(HoaDon.class);
                            if (hoaDon != null && isDateInRange(hoaDon.getNgaytao(), startDate, endDate)) {

                                //Tính tổng doanh thu
                                totalRevenue += hoaDon.getGiatien();
                            }
                        }

                        // Hiển thị kết quả
                        txttong.setText(String.format(Locale.getDefault(), "%d VNĐ", totalRevenue));
                        Toast.makeText(getContext(), "Thống kê thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Thống kê thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Phương thức kiểm tra xem ngày có nằm trong khoảng thống kê không
    private boolean isDateInRange(String targetDate, String startDate, String endDate) {
        return targetDate.compareTo(startDate) >= 0 && targetDate.compareTo(endDate) <= 0;
    }
}