package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "shelters")
public class Shelter extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String charityNumber;
    @DatabaseField
    private String address;
    @DatabaseField
    private String gps;
    @DatabaseField
    private String email;

    public Shelter() {
        // ORMLite needs a no-arg constructor
    }

}