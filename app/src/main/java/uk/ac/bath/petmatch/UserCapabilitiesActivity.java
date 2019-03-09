package uk.ac.bath.petmatch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.DbHelper;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Database.UserPropertiesDao;
import uk.ac.bath.petmatch.Utils.UserCapabilitiesFragment;

/**
 * Activity should only run when user is logged in.
 */
public class UserCapabilitiesActivity extends BaseActivity {

    private UserCapabilitiesFragment userCapabilitiesFragment;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_capabilities);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Capabilities");

        // get user properties
        currentUser = loginService.getLoggedInUser();
        UserPropertiesDao userPropertiesDao = db.userProperties;
        UserProperties userProperties = userPropertiesDao.findByUserId(currentUser.getId());

        // Create new properties entry in database for user if they do not have one already.
        if(userProperties == null) {

            UserProperties newUserCapabilities = new UserProperties(false, false,
                    false, false, false, currentUser);
            userPropertiesDao.create(newUserCapabilities);
        }
        userCapabilitiesFragment = new UserCapabilitiesFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, userCapabilitiesFragment).commit();
    }

    /**
     * Updates the given user's capabilities/properties in the database.
     */
    public void updateUserCapabilities() {

        currentUser = loginService.getLoggedInUser();
        UserPropertiesDao userPropertiesDao = db.userProperties;
        UserProperties userProperties = userPropertiesDao.findByUserId(currentUser.getId());
        SharedPreferences preferences = userCapabilitiesFragment.getPreferenceManager().getSharedPreferences();

        userProperties.setHasKids(preferences.getBoolean("pref_kids", false));
        userProperties.setHasCatAllergies(preferences.getBoolean("pref_cat_allergy", false));
        userProperties.setHasDogAllergies(preferences.getBoolean("pref_dog_allergy", false));
        userProperties.setGreenAreas(preferences.getBoolean("pref_garden", false));
        userProperties.setFreeTime(preferences.getBoolean("pref_exercise", false));
        userPropertiesDao.update(userProperties);
    }

    public UserProperties getUserProperties(){

        currentUser = loginService.getLoggedInUser();
        UserPropertiesDao userPropertiesDao = db.userProperties;
        return userPropertiesDao.findByUserId(currentUser.getId());
    }
}