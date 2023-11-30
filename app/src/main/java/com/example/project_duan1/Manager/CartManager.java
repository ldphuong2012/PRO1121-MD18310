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
    public GioHang getCartItemByName(String itemName) {
        for (GioHang item : cartItems) {
            if (item.getName_pr().equals(itemName)) {
                return item;
            }
        }
        return null; // Return null if item not found
    }
    public void updateCartItem(GioHang updatedItem) {
        for (int i = 0; i < cartItems.size(); i++) {
            GioHang currentItem = cartItems.get(i);
            if (currentItem.getName_pr().equals(updatedItem.getName_pr())) {
                // Update the properties of the existing item
                currentItem.setNumber_pr(updatedItem.getNumber_pr());
                // You can update other properties as needed

                // Optionally, break the loop if you've found the item
                break;
            }
        }
        // Notify any listeners or update UI as needed
        // ...
    }


    // Add any other methods you may need
}
