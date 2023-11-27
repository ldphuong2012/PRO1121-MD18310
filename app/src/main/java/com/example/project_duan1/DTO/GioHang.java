package com.example.project_duan1.DTO;

public class GioHang {
    String id_pr;
    String img_pr;
    String name_pr;
    String price_pr;

    public GioHang(String id_pr, String img_pr, String name_pr, String price_pr) {
        this.id_pr = id_pr;
        this.img_pr = img_pr;
        this.name_pr = name_pr;
        this.price_pr = price_pr;
    }

    public String getId_pr() {
        return id_pr;
    }

    public void setId_pr(String id_pr) {
        this.id_pr = id_pr;
    }

    public String getImg_pr() {
        return img_pr;
    }

    public void setImg_pr(String img_pr) {
        this.img_pr = img_pr;
    }

    public String getName_pr() {
        return name_pr;
    }

    public void setName_pr(String name_pr) {
        this.name_pr = name_pr;
    }

    public String getPrice_pr() {
        return price_pr;
    }

    public void setPrice_pr(String price_pr) {
        this.price_pr = price_pr;
    }

    public GioHang() {
    }
}
