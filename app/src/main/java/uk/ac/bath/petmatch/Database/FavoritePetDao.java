package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

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
}
