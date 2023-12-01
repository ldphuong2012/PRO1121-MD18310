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

import com.example.project_duan1.Detail.DetailHoaDon;
import com.example.project_duan1.Model.HoaDon;
import com.example.project_duan1.Model.NhanVien;
import com.example.project_duan1.R;

import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> implements Filterable {

    private ArrayList<HoaDon> mListHoadon;
    private ArrayList<HoaDon> mListHoadonOld;
    private Context context;
    private IclickListenner mIclickListenner;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListHoadon = mListHoadonOld;
                }
                else {
                    ArrayList<HoaDon> list = new ArrayList<>();
                    for (HoaDon hoaDon : mListHoadonOld){
                        if (hoaDon.getNgaytao().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(hoaDon);
                        }
                    }
                    mListHoadon = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListHoadon;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListHoadon = (ArrayList<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IclickListenner {
        void onClickDeleteItem1(HoaDon hoaDon);
    }

    public HoaDonAdapter(ArrayList<HoaDon> mListHoadon, Context context, IclickListenner listenner) {
        this.mListHoadon = mListHoadon;
        this.mListHoadonOld = mListHoadon;
        this.context = context;
        this.mIclickListenner = listenner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recclerview_hoadon,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HoaDon hoaDon = mListHoadon.get(position);
        if (hoaDon == null){
            return;
        }
        holder.txtmahd.setText("Mã hóa đơn: "+mListHoadon.get(position).getMahoadon());
        holder.txtgiatien.setText("Tổng tiền: "+String.valueOf(mListHoadon.get(position).getGiatien()));
        holder.txtngaytao.setText("Ngày tạo: "+mListHoadon.get(position).getNgaytao());

        holder.imgdelete_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListenner.onClickDeleteItem1(hoaDon);
            }
        });

        holder.layout_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotoDetail(hoaDon);
            }
        });
    }

    private void onClickGotoDetail(HoaDon hoaDon){
        Intent intent = new Intent(context, DetailHoaDon.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objec_ten",hoaDon);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        if (mListHoadon != null){
            return mListHoadon.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtmahd,txtgiatien,txtngaytao;
        private ImageView imgdelete_hoadon;
        private CardView layout_item1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmahd = itemView.findViewById(R.id.txtmahoadon);
            txtgiatien = itemView.findViewById(R.id.txtgiatienhd);
            txtngaytao = itemView.findViewById(R.id.txtngaytao);

            imgdelete_hoadon = itemView.findViewById(R.id.ivdeletehoadon);
            layout_item1 = itemView.findViewById(R.id.layout_itemhoadon);
        }
    }
}
