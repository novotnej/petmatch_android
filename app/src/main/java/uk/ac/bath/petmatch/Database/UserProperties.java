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
    private boolean freeTime;
    @DatabaseField
    private boolean greenAreas;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private User user;

    public UserProperties() {
        // ORMLite needs a no-arg constructor
    }

    public UserProperties(boolean hasDogAllergies, boolean hasCatAllergies, boolean hasKids, boolean freeTime, boolean greenAreas, User user) {
        this.id = generateId();
        this.hasDogAllergies = hasDogAllergies;
        this.hasCatAllergies = hasCatAllergies;
        this.hasKids = hasKids;
        this.freeTime = freeTime;
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

    public boolean getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(boolean freeTime) {
        this.freeTime = freeTime;
    }

    public boolean getGreenAreas() {
        return greenAreas;
    }

    public void setGreenAreas(boolean greenAreas) {
        this.greenAreas = greenAreas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasFreeTime() {
        return freeTime;
    }
}