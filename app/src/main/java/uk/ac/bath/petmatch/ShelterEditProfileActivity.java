package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;

public class ShelterEditProfileActivity extends BaseActivity implements View.OnClickListener {

    private EditText shelterIntroduction;
    private EditText shelterAddress;
    private EditText shelterEmail;
    private EditText shelterCharityNumber;
    private EditText shelterPhoneNumber;
    private Shelter currentShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_edit_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Shelter Profile");

        ShelterDao shelterDao = db.shelters;
        currentShelter = shelterDao.loadOneRandom();

        shelterIntroduction = (EditText) findViewById(R.id.shelter_introduction);
        shelterAddress = (EditText) findViewById(R.id.shelter_address);
        shelterEmail = (EditText) findViewById(R.id.shelter_email);
        shelterCharityNumber = (EditText) findViewById(R.id.shelter_charity_no);
        shelterPhoneNumber = (EditText) findViewById(R.id.shelter_phone);

        if(currentShelter != null) {

            shelterIntroduction.setText(currentShelter.getDescription());
            shelterAddress.setText(currentShelter.getAddress());
            shelterEmail.setText(currentShelter.getEmail());
            shelterCharityNumber.setText(currentShelter.getCharityNumber());
            shelterPhoneNumber.setText(currentShelter.getPhoneNumber());
        }

        Button saveButton = (Button) findViewById(R.id.save_button_shelter);
        saveButton.setOnClickListener(this);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.shelter_map);
//        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
//        params.height = 850;
//        mapFragment.setHasOptionsMenu(true);
//        mapFragment.setMenuVisibility(true);
//        mapFragment.getView().setLayoutParams(params);
//        mapFragment.setMenuVisibility(true);
//        ShelterDao shelterDao = db.shelters;
//        currentShelter = shelterDao.loadOneRandom();
//
//        mapFragment.getMapAsync(this);
//
//        shelterTitle.setText(currentShelter.getTitle());
//        shelterIntroduction.setText(currentShelter.getDescription());
//        shelterAddress.setText(currentShelter.getAddress());
//        shelterEmail.setText(currentShelter.getEmail());
//        shelterEmail.setOnClickListener(emailClick);
//        shelterCharityNumber.setText(currentShelter.getCharityNumber());
//        shelterPhoneNumber.setText(currentShelter.getPhoneNumber());
    }

    @Override
    public void onClick(View v) {



    }
}

