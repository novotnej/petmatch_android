package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.ShelterDao;

public class ShelterEditProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_edit_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Shelter Profile");

        EditText shelterAddress = (EditText) findViewById(R.id.shelter_address);
        EditText shelterEmail = (EditText) findViewById(R.id.shelter_email);
        EditText shelterCharityNumber = (EditText) findViewById(R.id.shelter_charity_no);
        EditText shelterPhoneNumber = (EditText) findViewById(R.id.shelter_phone);

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
}

