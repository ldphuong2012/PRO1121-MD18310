package com.example.project_duan1.DTO;

public class Favourite {
    String id_pr_favourite;
    String img_pr_favourite;
    String name_pr_favourite;
    String price_pr_favourite;

    public Favourite() {
    }

    public Favourite(String id_pr_favourite, String img_pr_favourite, String name_pr_favourite, String price_pr_favourite) {
        this.id_pr_favourite = id_pr_favourite;
        this.img_pr_favourite = img_pr_favourite;
        this.name_pr_favourite = name_pr_favourite;
        this.price_pr_favourite = price_pr_favourite;
    }

    public String getId_pr_favourite() {
        return id_pr_favourite;
    }

    public void setId_pr_favourite(String id_pr_favourite) {
        this.id_pr_favourite = id_pr_favourite;
    }

    public String getImg_pr_favourite() {
        return img_pr_favourite;
    }

    public void setImg_pr_favourite(String img_pr_favourite) {
        this.img_pr_favourite = img_pr_favourite;
    }

    public String getName_pr_favourite() {
        return name_pr_favourite;
    }

    public void setName_pr_favourite(String name_pr_favourite) {
        this.name_pr_favourite = name_pr_favourite;
    }

    public String getPrice_pr_favourite() {
        return price_pr_favourite;
    }

    public void setPrice_pr_favourite(String price_pr_favourite) {
        this.price_pr_favourite = price_pr_favourite;
    }
}
