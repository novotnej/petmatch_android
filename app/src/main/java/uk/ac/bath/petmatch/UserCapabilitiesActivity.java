package uk.ac.bath.petmatch;

import android.os.Bundle;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Database.UserPropertiesDao;
import uk.ac.bath.petmatch.Utils.UserCapabilitiesFragment;

/**
 * Activity should only run when user is logged in.
 */
public class UserCapabilitiesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_capabilities);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Capabilities");

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null ) {
                return;
            }
            else {
                getFragmentManager().beginTransaction().add(R.id.fragment_container, new UserCapabilitiesFragment()).commit();
            }
        }
        createDbHelper();
        User currentUser = loginService.getLoggedInUser();
        UserPropertiesDao userPropertiesDao = db.userProperties;
        UserProperties userProperties = userPropertiesDao.findByUserId(currentUser.getId());

        // check if the user has properties in the database. Otherwise, create new properties.
        if(userProperties == null) {

            userPropertiesDao.createNewUserProperties(currentUser);
        }
        System.out.println(userProperties);
    }
}