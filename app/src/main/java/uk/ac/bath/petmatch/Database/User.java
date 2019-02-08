package uk.ac.bath.petmatch.Database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "users")
public class User extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;
    @DatabaseField(foreign = true, canBeNull = true)
    private Shelter shelter;

    public User() {
        // ORMLite needs a no-arg constructor
    }
    public User(String id, String name, String email, Shelter shelter) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.shelter = shelter;
    }


}