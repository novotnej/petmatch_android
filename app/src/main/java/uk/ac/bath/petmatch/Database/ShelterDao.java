package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class ShelterDao extends RuntimeExceptionDao {
    public ShelterDao(Dao<Shelter, String> dao) {
        super(dao);
    }

    private ArrayList<Shelter> convertListToArrayList(List<Shelter> list) {
        ArrayList<Shelter> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
