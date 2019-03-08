package uk.ac.bath.petmatch.Database;


import java.io.Serializable;

public class CommonModel implements Serializable {
    protected transient String id; //has to be transient so it is not included in GSON (API use), only the custom implementations

    public String getId() {
        return this.id;
    }

    public String generateId () {
        return randomAlphaNumeric(12);
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String toString() {
        return this.getId();
    }
}
