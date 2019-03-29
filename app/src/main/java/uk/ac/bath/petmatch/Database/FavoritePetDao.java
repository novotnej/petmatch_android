package uk.ac.bath.petmatch.Database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritePetDao extends RuntimeExceptionDao {
    public FavoritePetDao(Dao<FavoritePet, String> dao) {
        super(dao);
    }

    private ArrayList<FavoritePet> convertListToArrayList(List<FavoritePet> list) {
        ArrayList<FavoritePet> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }

    private FavoritePet findFavorite(User user, Pet pet) {
        if (user == null || pet == null) {
            return null;
        }
        try {
            return (FavoritePet) this.queryBuilder()
                    .where().eq("user_id", user.getId())
                    .and().eq("pet_id", pet.getId())
                    .queryForFirst();
        } catch (SQLException e) {
            Log.e("RemoveFromFavorites", e.getMessage());
        }
        return null;
    }

    public boolean isFavourite(User user, Pet pet) {
        if (findFavorite(user, pet) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void addToFavourites(User user, Pet pet) {
        if (user == null || pet == null) {
            return;
        }

        FavoritePet favorite = findFavorite(user, pet);

        if (favorite == null) {
            favorite = new FavoritePet(pet, user);
            this.create(favorite);
        }
    }

    public void removeFromFavourites(User user, Pet pet) {
        if (user == null || pet == null) {
            return;
        }
        FavoritePet favorite = findFavorite(user, pet);

        if (favorite != null) {
            this.delete(favorite);
        }
    }

    public ArrayList<FavoritePet> getFavourites(User user) {
        if (user == null) {
            return null;
        }
        List<FavoritePet> results = this.queryForEq("user_id", user.getId());
        return this.convertListToArrayList(results);
    }
}
