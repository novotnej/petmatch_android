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
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = true)
    private Shelter shelter;

    public User() {
        // ORMLite needs a no-arg constructor
    }

    public User(String name, String email, String password, Shelter shelter) {
        this.id = generateId();
        this.name = name;
        this.email = email;
        this.password = password;
        this.shelter = shelter;
    }


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}