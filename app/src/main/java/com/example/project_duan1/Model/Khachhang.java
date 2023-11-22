package com.example.project_duan1.Model;

import java.util.HashMap;
import java.util.Map;

public class Khachhang {
    public Khachhang() {
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDaiChi() {
        return daiChi;
    }

    public void setDaiChi(String daiChi) {
        this.daiChi = daiChi;
    }

    public String getSoDienThoaiKH() {
        return soDienThoaiKH;
    }

    public void setSoDienThoaiKH(String soDienThoaiKH) {
        this.soDienThoaiKH = soDienThoaiKH;
    }

    public Khachhang(String makh, String hoTen, String daiChi, String soDienThoaiKH) {
        this.makh = Integer.parseInt(makh);
        this.hoTen = hoTen;
        this.daiChi = daiChi;
        this.soDienThoaiKH = soDienThoaiKH;
    }

    private int makh;
    private String hoTen;
    private String daiChi;
    private String soDienThoaiKH;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("makh",makh);
        result.put("hoTen",hoTen);
        result.put("daiChi",daiChi);
        result.put("sdt",soDienThoaiKH);

        return result;
    }
}
