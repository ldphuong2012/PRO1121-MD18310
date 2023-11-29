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
    public void DelFavorite(String id) {
        // Tìm vị trí của mục trong danh sách yêu thích dựa trên id
        int index = -1;
        for (int i = 0; i < favouriteList.size(); i++) {
            Favourite item = favouriteList.get(i);
            if (item.getId_pr_favourite().equals(id)) {
                index = i;
                break;
            }
        }

        // Xóa mục khỏi danh sách yêu thích nếu tìm thấy
        if (index != -1) {
            favouriteList.remove(index);
        }
    }

    // Add any other methods you may need
}
