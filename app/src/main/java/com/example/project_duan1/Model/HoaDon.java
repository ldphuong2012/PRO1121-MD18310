package com.example.project_duan1.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HoaDon implements Serializable {
    private String mahoadon;
    private String tenkhachhang;
    private String tennhanvien;
    private ArrayList<GioHangHoaDon> gioHangList;
    private double giatien;
    private String ngaytao;
    private String giotao;

    public HoaDon() {
    }

    public HoaDon(String mahoadon, String tenkhachhang, String tennhanvien, ArrayList<GioHangHoaDon> gioHangList , double giatien, String ngaytao, String giotao) {
        this.mahoadon = mahoadon;
        this.tenkhachhang = tenkhachhang;
        this.tennhanvien = tennhanvien;
        this.gioHangList = gioHangList;
        this.giatien = giatien;
        this.ngaytao = ngaytao;
        this.giotao = giotao;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public ArrayList<GioHangHoaDon> getGioHangList() {
        return gioHangList;
    }

    public void setGioHangList(ArrayList<GioHangHoaDon> gioHangList) {
        this.gioHangList = gioHangList;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }

    public double getGiatien() {
        return giatien;
    }


    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getGiotao() {
        return giotao;
    }

    public void setGiotao(String giotao) {
        this.giotao = giotao;
    }
}
