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
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private User user;

    public UserProperties() {
        // ORMLite needs a no-arg constructor
    }

    public UserProperties(boolean hasDogAllergies, boolean hasCatAllergies, boolean hasKids, int workHours, int accommodation, int greenAreas, User user) {
        this.id = generateId();
        this.hasDogAllergies = hasDogAllergies;
        this.hasCatAllergies = hasCatAllergies;
        this.hasKids = hasKids;
        this.workHours = workHours;
        this.accommodation = accommodation;
        this.greenAreas = greenAreas;
        this.user = user;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasDogAllergies() {
        return hasDogAllergies;
    }

    public void setHasDogAllergies(boolean hasDogAllergies) {
        this.hasDogAllergies = hasDogAllergies;
    }

    public boolean hasCatAllergies() {
        return hasCatAllergies;
    }

    public void setHasCatAllergies(boolean hasCatAllergies) {
        this.hasCatAllergies = hasCatAllergies;
    }

    public boolean hasKids() {
        return hasKids;
    }

    public void setHasKids(boolean hasKids) {
        this.hasKids = hasKids;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(int accommodation) {
        this.accommodation = accommodation;
    }

    public int getGreenAreas() {
        return greenAreas;
    }

    public void setGreenAreas(int greenAreas) {
        this.greenAreas = greenAreas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}