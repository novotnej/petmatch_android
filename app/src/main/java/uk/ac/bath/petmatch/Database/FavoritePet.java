package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "favorite_pets")
public class FavoritePet extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField(foreign = true, canBeNull = false)
    private Pet pet;
    @DatabaseField(foreign = true, canBeNull = false)
    private User user;

    public FavoritePet() {
        // ORMLite needs a no-arg constructor
    }
}