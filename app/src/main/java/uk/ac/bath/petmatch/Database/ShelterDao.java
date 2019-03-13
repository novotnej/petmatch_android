package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class ShelterDao extends RuntimeExceptionDao {
    public ShelterDao(Dao<Shelter, String> dao) {
        super(dao);
    }

    public Shelter findByTitle(String shelterTitle) {
        List<Shelter> results = this.queryForEq("title", shelterTitle);
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    private ArrayList<Shelter> convertListToArrayList(List<Shelter> list) {
        ArrayList<Shelter> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
