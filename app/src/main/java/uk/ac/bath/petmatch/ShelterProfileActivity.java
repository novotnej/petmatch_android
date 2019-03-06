package uk.ac.bath.petmatch;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.Objects;

public class ShelterProfileActivity extends BaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shelter_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");
    }
}
