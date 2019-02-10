package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class PetDao extends RuntimeExceptionDao {
    public PetDao(Dao<Pet, String> dao) {
        super(dao);
    }

    private ArrayList<Pet> convertListToArrayList(List<Pet> list) {
        ArrayList<Pet> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
