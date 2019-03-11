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
    private double lat; //latitude
    @DatabaseField
    private double lon; //longitude
    @DatabaseField
    private String email;

    public Shelter() {
        // ORMLite needs a no-arg constructor
    }

    public Shelter(String title, String description, String charityNumber, String address, String gps, String email) {
        this.id = generateId();
        this.title = title;
        this.description = description;
        this.charityNumber = charityNumber;
        this.address = address;
        setLatLonFromGps(gps);
        this.email = email;
    }

    private void setLatLonFromGps(String gps) {
        String[] latlon = gps.split(",");
        this.lat = Double.parseDouble(latlon[0]);
        this.lon = Double.parseDouble(latlon[1]);
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
        return this.lat + "," + this.lon;
    }

    public void setGps(String gps) {
        this.setLatLonFromGps(gps);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}