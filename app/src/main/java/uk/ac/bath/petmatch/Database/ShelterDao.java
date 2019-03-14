package uk.ac.bath.petmatch.Database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShelterDao extends RuntimeExceptionDao {
    public ShelterDao(Dao<Shelter, String> dao) {
        super(dao);
    }

    public Shelter loadOneRandom() {
        List<Shelter> results = null;
        try {
            results = this.queryBuilder().orderByRaw("RANDOM()").limit((long) 1).query();
        } catch (SQLException e) {
            Log.e("Shelter load random", e.getMessage());
            return null;
        }
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
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
