package com.example.project_duan1.DTO;

public class Product {
    private String ID_pr;
    private String Image;
    private String Name;
    private String Price;
    private String TypeProduct;
    private String Number;
    private String Description;
    private String key;
    public Product(){

    }

    public Product(String ID_pr, String image, String name, String price, String typeProduct, String number, String description) {
        this.ID_pr = ID_pr;
        Image = image;
        Name = name;
        Price = price;
        TypeProduct = typeProduct;
        Number = number;
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTypeProduct() {
        return TypeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        TypeProduct = typeProduct;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
