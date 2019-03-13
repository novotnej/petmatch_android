package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.User;

public class ShelterPublicProfileActivity extends BaseActivity {

    private Shelter currentShelter;
    private User currentUser;
    private KeyListener listenerAddress;
    private KeyListener listenerEmail;
    private KeyListener listenerCharityNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_public_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");

        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title);
        TextView shelterIntroduction = (TextView) findViewById(R.id.shelter_introduction);
        EditText shelterAddress = (EditText)findViewById(R.id.shelter_address);
        EditText shelterEmail = (EditText)findViewById(R.id.shelter_email);
        EditText shelterCharityNumber = (EditText)findViewById(R.id.shelter_charity_no);
        MapView shelterMap = (MapView)findViewById(R.id.mapShelter);

        // assign listeners to the variables to be able to make the fields editable later.
        listenerAddress = shelterAddress.getKeyListener();
        listenerEmail = shelterEmail.getKeyListener();
        listenerCharityNumber = shelterCharityNumber.getKeyListener();

        // set the listeners to null making the fields uneditable.
        shelterAddress.setKeyListener(null);
        shelterEmail.setKeyListener(null);
        shelterCharityNumber.setKeyListener(null);

        // get current shelter and set all fields to match it.
        boolean isUserLoggedIn = loginService.isUserLoggedIn();
        if(isUserLoggedIn && (currentShelter = loginService.getLoggedInUser().getShelter()) != null) {

            shelterTitle.setText(currentShelter.getTitle());
            shelterIntroduction.setText(currentShelter.getDescription());
            shelterAddress.setText(currentShelter.getAddress());
            shelterEmail.setText(currentShelter.getEmail());
            shelterCharityNumber.setText(currentShelter.getCharityNumber());
        }
    }

    public KeyListener getListenerAddress() {
        return listenerAddress;
    }

    public KeyListener getListenerEmail() {
        return listenerEmail;
    }

    public KeyListener getListenerCharityNumber() {
        return listenerCharityNumber;
    }
}
