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

    public Pet(String title, String description, Shelter shelter, PetBreed breed) {
        this.title = title;
        this.description = description;
        this.shelter = shelter;
        this.breed = breed;
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