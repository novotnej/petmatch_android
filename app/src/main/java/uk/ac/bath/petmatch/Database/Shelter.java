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
    @DatabaseField
    private String phoneNumber;

    public Shelter() {
        // ORMLite needs a no-arg constructor
    }

    public Shelter(String title, String description, String charityNumber, String address, String gps, String email, String phoneNumber) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.charityNumber = charityNumber;
        this.address = address;
        this.gps = gps;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getCharityNumber() {
        return charityNumber;
    }

    public void setCharityNumber(String charityNumber) {
        this.charityNumber = charityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}