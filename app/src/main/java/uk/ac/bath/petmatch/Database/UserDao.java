package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class UserDao extends RuntimeExceptionDao {
    public UserDao(Dao<User, String> dao) {
        super(dao);
    }

    public User findByEmail(String email) {
        List<User> results = this.queryForEq("email", email);
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    private ArrayList<User> convertListToArrayList(List<User> list) {
        ArrayList<User> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
