package uk.ac.bath.petmatch.Database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

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

    public ArrayList<Pet> loadByShelterId(String shelterId) {
        List<Pet> results = this.queryForEq("shelter_id", shelterId);
        return this.convertListToArrayList(results);
    }


    public ArrayList<Pet> loadByFilter(PetBreedDao petBreedDao, String petType, String petBreedId, UserProperties userProperties, double lat, double lon, int distance) {
        QueryBuilder<Pet, String> query = queryBuilder();
        QueryBuilder<PetBreed, String> petBreedBuilder = petBreedDao.queryBuilder();
        final Where<PetBreed, String> w = petBreedBuilder.where();
        try {
            w.isNotNull("id");
            if (petBreedId != null) {
                query.where().eq("breed_id", petBreedId);
            }

            if (userProperties != null) {
                //Where<PetBreed, String> where = petBreedBuilder.where();
                if (userProperties.hasCatAllergies()) {
                    w.and().eq("causesCatAllergies", false);
                }
                if (userProperties.hasDogAllergies()) {
                    w.and().eq("causesDogAllergies", false);
                }
                if (userProperties.hasKids()) {
                    w.and().eq("childrenFriendly", true);
                }
                if (!userProperties.hasFreeTime()) {
                    w.and().eq("laborIntensive", false);
                }
                if (!userProperties.getGreenAreas()) {
                    w.and().eq("spaceIntensive", false);
                }
            }

            if (petType != null) {
                w.and().eq("type", petType);
            }

            petBreedBuilder.setWhere(w);
            query.join("breed_id", "id", petBreedBuilder);


            List<Pet> queryResults = query.query();
            ArrayList<Pet> arrayList = new ArrayList<>();
            //This is a terrible way of doing it, but sqlite does not support any trigonometric functions directly
            for (int i = 0; i < queryResults.size(); i++) {
                Pet pet = queryResults.get(i);
                if (calculateDistance(pet.getShelter(), lat, lon) <= distance) {
                    arrayList.add(pet);
                }
            }

            return arrayList;


        } catch (SQLException e) {
            Log.e("PetDao", e.getMessage());
            return null;
        }
    }

    public double calculateDistance(Shelter shelter, double lat, double lon) {

        double x1 = Math.toRadians(lat);
        double y1 = Math.toRadians(lon);
        double x2 = Math.toRadians(shelter.getLat());
        double y2 = Math.toRadians(shelter.getLon());

        /* Compute great circle distance using Haversine formula
        *************************************************************************/
        double a = Math.pow(Math.sin((x2-x1)/2), 2)
                + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2-y1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;
        return distance2 * 1.852; //convert NM to km
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
