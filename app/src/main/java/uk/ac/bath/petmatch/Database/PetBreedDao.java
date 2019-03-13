package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

public class PetBreedDao extends RuntimeExceptionDao {
    public PetBreedDao(Dao<PetBreed, String> dao) {
        super(dao);
    }

    public String[] getArrayForType(String type) {
        String[] breeds;
        List<PetBreed> results;
        if (type == null) {
            results = queryForAll();
        } else {
            results = this.queryForEq("type", type);
        }


        breeds = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            breeds[i] = results.get(i).getTitle();
        }

        return breeds;
    }

    public PetBreed loadByTitle(String title) {
        List<PetBreed> results = queryForEq("title", title);
        if (results.size() == 0) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public ArrayList<PetBreed> getDummy() {
        return this.convertListToArrayList(
                this.queryForAll()
        );
    }

    private ArrayList<PetBreed> convertListToArrayList(List<PetBreed> list) {
        ArrayList<PetBreed> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
