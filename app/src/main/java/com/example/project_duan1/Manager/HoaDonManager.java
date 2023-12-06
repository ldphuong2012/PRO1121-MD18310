package com.example.project_duan1.Manager;

import com.example.project_duan1.Model.GioHangHoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonManager {
    private static HoaDonManager instance;
    private ArrayList<GioHangHoaDon> gioHangHoaDons;

    private HoaDonManager(){
        gioHangHoaDons = new ArrayList<>();
    }
    public static HoaDonManager getInstance(){
        if (instance == null){
            instance = new HoaDonManager();
        }
        return instance;
    }
    public List<GioHangHoaDon> getHoaDonItems(){
        return gioHangHoaDons;
    }
    public void addSanPhamHD(GioHangHoaDon items){
        gioHangHoaDons.add(items);
    }
    public GioHangHoaDon getHoaDonByName(String ItemsName){
        for (GioHangHoaDon item : gioHangHoaDons){
            if (item.getTensp().equals(ItemsName)){
                return item;
            }
        }
        return null;
    }
    public void updateSanPhamHD(GioHangHoaDon updateItem){
        for (int i = 0; i < gioHangHoaDons.size(); i++){
             GioHangHoaDon currentItem = gioHangHoaDons.get(i);
             if (currentItem.getTensp().equals(updateItem.getTensp())){
                 currentItem.setSoluong(updateItem.getSoluong());
                 break;
             }
        }
    }

}
