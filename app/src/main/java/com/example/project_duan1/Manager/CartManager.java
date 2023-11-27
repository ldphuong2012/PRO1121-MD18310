package com.example.project_duan1.Manager;

import com.example.project_duan1.DTO.GioHang;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<GioHang> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<GioHang> getCartItems() {
        return cartItems;
    }

    public void addToCart(GioHang item) {
        cartItems.add(item);
    }

    // Add any other methods you may need
}
