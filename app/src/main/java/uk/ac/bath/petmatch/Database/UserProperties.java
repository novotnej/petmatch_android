package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_properties")
public class UserProperties extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private boolean hasDogAllergies;
    @DatabaseField
    private boolean hasCatAllergies;
    @DatabaseField
    private boolean hasKids;
    @DatabaseField
    private int workHours;
    @DatabaseField
    private int accommodation;
    @DatabaseField
    private int greenAreas;
    @DatabaseField(foreign = true, canBeNull = false)
    private User user;

    public UserProperties() {
        // ORMLite needs a no-arg constructor
    }
}