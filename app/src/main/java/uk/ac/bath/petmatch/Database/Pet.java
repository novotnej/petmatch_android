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

    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3, canBeNull = false)
    private Shelter shelter;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3, canBeNull = true)
    private PetBreed breed;

    public Pet() {
        // ORMLite needs a no-arg constructor
    }

    public Pet(String title, String description, Shelter shelter, PetBreed breed) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.shelter = shelter;
        this.breed = breed;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public PetBreed getBreed() {
        return breed;
    }

    public void setBreed(PetBreed breed) {
        this.breed = breed;
    }
}