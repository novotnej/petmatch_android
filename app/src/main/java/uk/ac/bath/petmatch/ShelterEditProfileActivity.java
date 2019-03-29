package uk.ac.bath.petmatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Database.UserPropertiesDao;

public class ShelterEditProfileActivity extends BaseActivity implements View.OnClickListener {

    private EditText shelterIntroduction;
    private AutoCompleteTextView shelterAddress;
    private EditText shelterEmail;
    private EditText shelterCharityNumber;
    private EditText shelterPhoneNumber;
    private Shelter currentShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User currentUser = loginService.getLoggedInUser();
        ShelterDao shelterDao = db.shelters;

        if(currentUser == null) {

            showPermissionError();
            return;
        }

        if(currentUser.getShelter() == null) {

            showPermissionError();
            return;
        }
        currentShelter = shelterDao.findById(currentUser.getShelter().getId());

        if(currentShelter == null) {

            showPermissionError();
            return;
        }
        if(!(currentUser.getShelter().getId().equals(currentShelter.getId()))) {

            showPermissionError();
            return;
        }
        setContentView(R.layout.activity_shelter_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Shelter Profile");

        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title_edit);
        shelterIntroduction = (EditText) findViewById(R.id.shelter_introduction_edit);
        shelterAddress = (AutoCompleteTextView) findViewById(R.id.shelter_address_edit);
        shelterEmail = (EditText) findViewById(R.id.shelter_email_edit);
        shelterCharityNumber = (EditText) findViewById(R.id.shelter_charity_no_edit);
        shelterPhoneNumber = (EditText) findViewById(R.id.shelter_phone_edit);

        if(currentShelter != null) {

            shelterTitle.setText(currentShelter.getTitle());
            shelterIntroduction.setText(currentShelter.getDescription());
            shelterAddress.setText(currentShelter.getAddress());
            shelterEmail.setText(currentShelter.getEmail());
            shelterCharityNumber.setText(currentShelter.getCharityNumber());
            shelterPhoneNumber.setText(currentShelter.getPhoneNumber());
        }
        Button saveButton = (Button) findViewById(R.id.save_button_shelter);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        ShelterDao shelterDao = db.shelters;
        currentShelter = shelterDao.findByTitle(currentShelter.getTitle());
        if(currentShelter != null) {

            currentShelter.setDescription(shelterIntroduction.getText().toString());
            currentShelter.setAddress(shelterAddress.getText().toString());
            currentShelter.setEmail(shelterEmail.getText().toString());
            currentShelter.setCharityNumber(shelterCharityNumber.getText().toString());
            currentShelter.setPhoneNumber(shelterPhoneNumber.getText().toString());
            shelterDao.update(currentShelter);
            showSuccessAlert(v);
        }
    }

    public void showSuccessAlert(View view) {

        AlertDialog.Builder successSave = new AlertDialog.Builder(this);
        successSave.setTitle("Success");
        successSave.setMessage("Your changes have been successfully saved");
        successSave.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent goBackToMainActivity = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(goBackToMainActivity);
            }
        });
        successSave.create();
        successSave.show();

    }

    public void showPermissionError() {

        AlertDialog.Builder permissionError = new AlertDialog.Builder(this);
        permissionError.setTitle("Permission Error");
        permissionError.setMessage("You do not have permission to edit the details of this shelter");
        permissionError.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent goBackToMainActivity = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(goBackToMainActivity);
            }
        });
        permissionError.create();
        permissionError.show();
    }

    ArrayList<String> placesList;
    ArrayAdapter<String> placesListAdatper;
    String placesURL;
    String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";

    protected void setUpAutocomplete() {
        shelterAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesList = new ArrayList<String>();
                updateList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateList(String place) {

        String parameter = "input=" + place + "&types=geocode&sensor=true&key=" + PLACES_API_KEY;

        placesURL = PLACES_BASE_URL + parameter;

        updateListsList(placesURL);

    }

    public void updatePlacesList(ArrayList<String> list) {
        placesList.clear();

        for (int i=0; i<list.size(); i++) {
            placesList.add(i, list.get(i).toString());
        }

        placesListAdatper = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, placesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                android.widget.TextView text = (android.widget.TextView) view.findViewById(android.R.id.text1);
                return view;
            }
        };

        shelterAddress.setAdapter(placesListAdatper);
        placesListAdatper.notifyDataSetChanged();
    }



    public void getAddressLatLong(String addressString) {
        Geocoder coder = new Geocoder(getBaseActivity());
        List<Address> address;
        LatLng latLng = null;

        try {
            address = coder.getFromLocationName(addressString, 5);
            if (address == null) {

            } else {
                Address location = address.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }
}

