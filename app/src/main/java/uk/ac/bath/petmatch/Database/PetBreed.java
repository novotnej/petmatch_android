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
    @DatabaseField
    private boolean causesCatAllergies;
    @DatabaseField
    private boolean causesDogAllergies;
    @DatabaseField
    private boolean childrenFriendly;
    @DatabaseField
    private boolean laborIntensive;

    public PetBreed() {
        // ORMLite needs a no-arg constructor
    }

    public PetBreed(String title, String type, boolean causesCatAllergies, boolean causesDogAllergies, boolean childrenFriendly, boolean laborIntensive) {
        this.id = generateId();
        this.title = title;
        this.type = type;
        this.causesCatAllergies = causesCatAllergies;
        this.causesDogAllergies = causesDogAllergies;
        this.childrenFriendly = childrenFriendly;
        this.laborIntensive = laborIntensive;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean causesCatAllergies() {
        return causesCatAllergies;
    }

    public void setCausesCatAllergies(boolean causesCatAllergies) {
        this.causesCatAllergies = causesCatAllergies;
    }

    public boolean causesDogAllergies() {
        return causesDogAllergies;
    }

    public void setCausesDogAllergies(boolean causesDogAllergies) {
        this.causesDogAllergies = causesDogAllergies;
    }

    public boolean isChildrenFriendly() {
        return childrenFriendly;
    }

    public void setChildrenFriendly(boolean childrenFriendly) {
        this.childrenFriendly = childrenFriendly;
    }

    public boolean isLaborIntensive() {
        return laborIntensive;
    }

    public void setLaborIntensive(boolean laborIntensive) {
        this.laborIntensive = laborIntensive;
    }
}