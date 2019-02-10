package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class PetBreedDao extends RuntimeExceptionDao {
    public PetBreedDao(Dao<PetBreed, String> dao) {
        super(dao);
    }

    private ArrayList<PetBreed> convertListToArrayList(List<PetBreed> list) {
        ArrayList<PetBreed> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
