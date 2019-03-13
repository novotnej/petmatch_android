package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class UserPropertiesDao extends RuntimeExceptionDao {
    public UserPropertiesDao(Dao<UserProperties, String> dao) {
        super(dao);
    }

    public UserProperties findByUserId(String userId) {
        List<UserProperties> results = this.queryForEq("user_id", userId);
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    private ArrayList<UserProperties> convertListToArrayList(List<UserProperties> list) {
        ArrayList<UserProperties> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }

    public UserProperties loadByUser(User user) {
        if (user != null) {
            List<UserProperties> results = this.queryForEq("user_id", user.getId());
            if (results.size() == 0) {
                return null;
            } else {
                return results.get(0);
            }
        }
        return null;
    }
}
