package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDao extends RuntimeExceptionDao {
    public PetDao(Dao<Pet, String> dao) {
        super(dao);
    }

    public ArrayList<Pet> getDummy() {
        return this.convertListToArrayList(
                this.queryForAll()
        );
    }

    public ArrayList<Pet> loadByFilter(PetBreedDao petBreedDao, String petType, String petBreedId, UserProperties userProperties) {
        QueryBuilder<Pet, String> query = queryBuilder();
        QueryBuilder<PetBreed, String> petBreedBuilder = petBreedDao.queryBuilder();
        try {


            if (petBreedId != null) {
                query.where().eq("breed_id", petBreedId);
            }

            if (userProperties != null) {
                if (userProperties.hasCatAllergies()) {
                    petBreedBuilder.where().eq("causesCatAllergies", false);
                }
                if (userProperties.hasDogAllergies()) {
                    petBreedBuilder.where().eq("causesDogAllergies", false);
                }
                if (userProperties.hasKids()) {
                    petBreedBuilder.where().eq("childrenFriendly", true);
                }
                if (!userProperties.hasFreeTime()) {
                    petBreedBuilder.where().eq("laborIntensive", false);
                }
                if (!userProperties.getGreenAreas()) {
                    petBreedBuilder.where().eq("spaceIntensive", false);
                }
            }

            if (petType != null) {
                petBreedBuilder.where().eq("type", petType);
            }

            query.join("breed_id", "id", petBreedBuilder);

            return this.convertListToArrayList(
                    query.query()
            );
        } catch (SQLException e) {
            return null;
        }
    }

    public Pet queryForId(String id) {
        return (Pet) super.queryForId(id);
    }

    private ArrayList<Pet> convertListToArrayList(List<Pet> list) {
        ArrayList<Pet> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }
}
