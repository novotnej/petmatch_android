package uk.ac.bath.petmatch;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.Objects;

import uk.ac.bath.petmatch.Utils.UserCapabilitiesFragment;

public class UserCapabilitiesActivity extends BaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_user_capabilities);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Capabilities");

        if(findViewById(R.id.fragment_container) != null) {

            if(savedInstanceState != null ) {

                return;
            }
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new UserCapabilitiesFragment()).commit();
        }
    }
}
