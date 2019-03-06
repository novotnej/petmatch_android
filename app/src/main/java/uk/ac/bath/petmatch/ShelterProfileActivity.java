package uk.ac.bath.petmatch;

import android.os.Bundle;

import java.util.Objects;

public class ShelterProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");
    }
}
