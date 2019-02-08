package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pets")
public class Pet extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;

    @DatabaseField(foreign = true, canBeNull = false)
    private Shelter shelter;
    @DatabaseField(foreign = true, canBeNull = true)
    private PetBreed breed;

    public Pet() {
        // ORMLite needs a no-arg constructor
    }
}