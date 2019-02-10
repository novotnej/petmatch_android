package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class UserPropertiesDao extends RuntimeExceptionDao {
    public UserPropertiesDao(Dao<UserProperties, String> dao) {
        super(dao);
    }

    private ArrayList<UserProperties> convertListToArrayList(List<UserProperties> list) {
        ArrayList<UserProperties> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
