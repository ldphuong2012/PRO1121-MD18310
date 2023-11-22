package com.example.project_duan1.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NhanVien implements Serializable {
    private String ten;
    private String tuoi;
    private String chucvu;
    private String sdt;
    private String email;

    public NhanVien() {
    }

    public NhanVien(String ten, String tuoi, String chucvu, String sdt, String email) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.chucvu = chucvu;
        this.sdt = sdt;
        this.email = email;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTuoi() {
        return tuoi;
    }

    public void setTuoi(String tuoi) {
        this.tuoi = tuoi;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ten",ten);
        result.put("tuoi",tuoi);
        result.put("chucvu",chucvu);
        result.put("sdt",sdt);
        result.put("email",email);

        return result;
    }
}
