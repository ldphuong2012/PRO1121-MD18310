package com.example.project_duan1.Manager;

import com.example.project_duan1.DTO.Favourite;
import com.example.project_duan1.DTO.GioHang;

import java.util.ArrayList;
import java.util.List;

public class FavouriteManager {
    private static FavouriteManager instance;
    private List<Favourite> favouriteList;

    private FavouriteManager() {
        favouriteList = new ArrayList<>();
    }

    public static FavouriteManager getInstance() {
        if (instance == null) {
            instance = new FavouriteManager();
        }
        return instance;
    }

    public List<Favourite> getFavouriteList() {
        return favouriteList;
    }

    public void addToFavorite(Favourite item) {
        favouriteList.add(item);
    }

    // Add any other methods you may need
}
