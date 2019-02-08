package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pet_breeds")
public class PetBreed extends CommonModel {

    public static final String  TYPE_CAT = "cat",
                                TYPE_DOG = "dog";

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String type;

    public PetBreed() {
        // ORMLite needs a no-arg constructor
    }

}