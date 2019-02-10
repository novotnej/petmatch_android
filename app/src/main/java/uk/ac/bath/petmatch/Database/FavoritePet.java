package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "favorite_pets")
public class FavoritePet extends CommonModel {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private Pet pet;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private User user;

    public FavoritePet() {
        // ORMLite needs a no-arg constructor
    }

    public FavoritePet(Pet pet, User user) {
        this.id = generateId();
        this.pet = pet;
        this.user = user;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}