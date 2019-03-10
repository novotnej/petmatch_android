package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.User;

public class ShelterPublicProfileActivity extends BaseActivity {

    private Shelter currentShelter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_public_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");

        TextView shelterIntroduction = (TextView) findViewById(R.id.shelter_introduction);
        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title);
        EditText shelterAddress = (EditText)findViewById(R.id.shelter_address);
        EditText shelterEmail = (EditText)findViewById(R.id.shelter_email);
        EditText charityNumber = (EditText)findViewById(R.id.shelter_phone);

        shelterAddress.setInputType(InputType.TYPE_NULL);
        shelterEmail.setInputType(InputType.TYPE_NULL);
        charityNumber.setInputType(InputType.TYPE_NULL);

        // get current shelter
        boolean isUserLoggedIn = loginService.isUserLoggedIn();
        if(isUserLoggedIn && (currentShelter = loginService.getLoggedInUser().getShelter()) != null) {

            System.out.println(currentShelter);
            System.out.println(currentShelter.getAddress());
            System.out.println(currentShelter.getCharityNumber());
            System.out.println(currentShelter.getDescription());
            System.out.println(currentShelter.getEmail());


//            shelterAddress.setText(currentShelter.getAddress());
//            shelterEmail.setText(currentShelter.getEmail());
//            charityNumber.setText(currentShelter.getCharityNumber());
        }
    }
}
