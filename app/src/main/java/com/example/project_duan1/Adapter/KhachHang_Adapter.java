package com.example.project_duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.DTO.Product;
import com.example.project_duan1.Model.Khachhang;
import com.example.project_duan1.R;

import java.util.ArrayList;
import java.util.List;

public class KhachHang_Adapter extends RecyclerView.Adapter<KhachHang_Adapter.ViewHolder> implements Filterable {


    private List<Khachhang> mlistKhachHang;
    private List<Khachhang> mlistKhachHangOLD;


    private IclickListenner mIclickListenner;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mlistKhachHang = mlistKhachHangOLD;
                } else {
                    List<Khachhang> list = new ArrayList<>();
                    for (Khachhang khachhang : mlistKhachHangOLD) {
                        if (khachhang.getSoDienThoaiKH().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(khachhang);
                        }
                    }
                    mlistKhachHang = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mlistKhachHang;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.values != null) {
                    mlistKhachHang = (List<Khachhang>) filterResults.values;
                    KhachHang_Adapter.onFiltered(mlistKhachHang);
                }
            }
        };
    }

    private static void onFiltered(List<Khachhang> mlistKhachHang) {
    }

    public interface IclickListenner {
        void onclickUpdateItem(Khachhang khachhang);

        void onClickDeleteItem(Khachhang khachhang);
    }

    public KhachHang_Adapter(List<Khachhang> mlistKhachHang, IclickListenner mIclickListenner) {
        this.mlistKhachHang = mlistKhachHang;
        this.mIclickListenner = mIclickListenner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_khachhang, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Khachhang khachhang = mlistKhachHang.get(position);
        if (khachhang == null) {
            return;
        }
        holder.txt_makhachhang.setText("Mã KH: " + khachhang.getMakh());
        holder.txt_tenkhachhang.setText("Tên KH: " + khachhang.getHoTen());
        holder.txt_diachi.setText("Địa Chỉ: " + khachhang.getDaiChi());
        holder.txt_sodienthoai.setText("Phone: " + khachhang.getSoDienThoaiKH());

        holder.edit_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListenner.onclickUpdateItem(khachhang);
            }
        });
        holder.delete_khachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListenner.onClickDeleteItem(khachhang);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mlistKhachHang != null) {
            return mlistKhachHang.size();
        }
        return 0;
    }
    public void  searchProduct(ArrayList<Khachhang> searchList){
        mlistKhachHang=searchList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_makhachhang, txt_tenkhachhang, txt_diachi, txt_sodienthoai;
        ImageButton delete_khachhang, edit_khachhang;

        CardView layout_itemKH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_makhachhang = itemView.findViewById(R.id.txt_makhachhang);
            txt_tenkhachhang = itemView.findViewById(R.id.txt_tenkhachhang);
            txt_diachi = itemView.findViewById(R.id.txt_diachi);
            txt_sodienthoai = itemView.findViewById(R.id.txt_sodienthoai);
            delete_khachhang = itemView.findViewById(R.id.delete_khachhang);
            edit_khachhang = itemView.findViewById(R.id.edit_khachhang);
            layout_itemKH = itemView.findViewById(R.id.layout_itemKH);

        }
    }

}