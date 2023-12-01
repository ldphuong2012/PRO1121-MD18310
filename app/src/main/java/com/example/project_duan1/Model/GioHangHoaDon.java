package com.example.project_duan1.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GioHangHoaDon implements Serializable {
    String idsp;
    String tensp;
    String giasp;
    String hinhsp;
    int soluong;

    public GioHangHoaDon() {
    }

    public GioHangHoaDon(String tensp, String giasp, String hinhsp, int soluong) {
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
