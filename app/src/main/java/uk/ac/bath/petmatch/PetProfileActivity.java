package uk.ac.bath.petmatch;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import static uk.ac.bath.petmatch.R.layout.activity_pet_profile;

public class PetProfileActivity extends BaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, activity_pet_profile);

        //Get the Intent that started this activity - useful for taking stuff from other ones if needed
        Intent intent = getIntent();
    }
}
