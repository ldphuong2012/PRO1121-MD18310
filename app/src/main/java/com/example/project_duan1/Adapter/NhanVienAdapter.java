package com.example.project_duan1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_duan1.Detail.DetailNhanvien;
import com.example.project_duan1.Model.NhanVien;
import com.example.project_duan1.R;

import java.util.ArrayList;
import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NhanvienHolder> implements Filterable {

    private List<NhanVien> mListThanhVien;
    private List<NhanVien> mListThanhVienOld;

    private IclickListenner mIclickListenner;

    private Context context;

    public interface IclickListenner {
        void onclickUpdateItem(NhanVien nhanVien);
        void onClickDeleteItem(NhanVien nhanVien);
    }

    public NhanVienAdapter(Context context ,List<NhanVien> mListThanhVien, IclickListenner listenner) {
        this.mListThanhVien = mListThanhVien;
        this.mListThanhVienOld = mListThanhVien;
        this.mIclickListenner = listenner;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListThanhVien = mListThanhVienOld;
                }
                else {
                    List<NhanVien> list = new ArrayList<>();
                    for (NhanVien nhanVien : mListThanhVienOld){
                        if (nhanVien.getTen().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(nhanVien);
                        }
                    }
                    mListThanhVien = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListThanhVien;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListThanhVien = (List<NhanVien>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public NhanvienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclenhanvien,parent,false);

        return new NhanvienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanvienHolder holder, int position) {

        NhanVien nhanVien = mListThanhVien.get(position);
        if (nhanVien == null){
            return;
        }
        holder.txtten.setText("Tên: " +nhanVien.getTen() );
        holder.txttuoi.setText("Tuổi: "+ nhanVien.getTuoi());
        holder.txtchucvu.setText("Chức vụ: "+ nhanVien.getChucvu());

        holder.imgupdatetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListenner.onclickUpdateItem(nhanVien);
            }
        });

        holder.imgxoathanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListenner.onClickDeleteItem(nhanVien);
            }
        });

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotoDetail(nhanVien);
            }
        });


    }

    private void onClickGotoDetail(NhanVien nhanVien){
        Intent intent = new Intent(context, DetailNhanvien.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objec_ten",nhanVien);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListThanhVien != null){
            return mListThanhVien.size();
        }
        return 0;
    }

    public class NhanvienHolder extends RecyclerView.ViewHolder {
        private TextView txtten,txttuoi,txtchucvu;
        private ImageView imgupdatetv,imgxoathanhvien;

        private CardView layout_item;
        public NhanvienHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.txttentv);
            txttuoi = itemView.findViewById(R.id.txttuoitv);
            txtchucvu = itemView.findViewById(R.id.txtchucvutv);
            imgupdatetv = itemView.findViewById(R.id.iveditthanhvien);
            imgxoathanhvien = itemView.findViewById(R.id.ivdeletethanhvien);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
    public void release() {
        context = null;
    }
}
