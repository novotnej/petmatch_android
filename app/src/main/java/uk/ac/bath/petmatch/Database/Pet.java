package uk.ac.bath.petmatch.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Random;

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
    @DatabaseField
    private String image;

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

    public static String getRandomImage(PetBreed breed) {
        String[] images;
        if (breed.getType().equals(PetBreed.TYPE_CAT)) {
            images = Pet.getCatImages();
        } else {
            images = Pet.getDogImages();
        }
        int randomNumber = (new Random()).nextInt(images.length);
        return images[randomNumber];
    }

    public static String getRandomImage() {
        String[] images = getAvailableImages();
        int randomNumber = (new Random()).nextInt(images.length);
        return images[randomNumber];
    }

    public static String[] getCatImages() {
        String[] cat_images = {"japanese_bobtail", "burmilla", "russian_blue", "ragdoll", "tabbycat", "shorthair", "siamese", "british_shorthair"};
        return cat_images;
    }

    public static String[] getDogImages() {
        String[] dog_images = {"lhasa", "labrador", "terrier", "beagle", "pekingese", "husky", "chihuahua", "daschshunds"};
        return dog_images;
    }

    public static String[] getAvailableImages() {
        String[] images = {"japanese_bobtail", "pekingese", "russian_blue", "ragdoll", "tabbycat", "shorthair", "siamese", "british_shorthair", "lhasa", "labrador", "terrier", "beagle", "burmilla", "husky", "chihuahua", "daschshunds"};
        return images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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