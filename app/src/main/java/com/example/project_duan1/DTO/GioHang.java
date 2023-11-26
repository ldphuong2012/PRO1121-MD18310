package com.example.project_duan1.DTO;

public class GioHang {
    String img_pr;
    String name_pr;
    String price_pr;
    String number_pr;

    public GioHang(String img_pr, String name_pr, String price_pr, String number_pr) {
        this.img_pr = img_pr;
        this.name_pr = name_pr;
        this.price_pr = price_pr;
        this.number_pr = number_pr;
    }

    public GioHang() {
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

    public String getNumber_pr() {
        return number_pr;
    }

    public void setNumber_pr(String number_pr) {
        this.number_pr = number_pr;
    }
}
