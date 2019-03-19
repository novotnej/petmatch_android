package uk.ac.bath.petmatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Database.UserPropertiesDao;

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



        User currentUser = loginService.getLoggedInUser();
        UserPropertiesDao userPropertiesDao = db.userProperties;


        if((currentUser == null) || !(currentUser.getShelter().equals(currentShelter))) {

            showPermissionError();
        }

        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title_edit);
        shelterIntroduction = (EditText) findViewById(R.id.shelter_introduction_edit);
        shelterAddress = (EditText) findViewById(R.id.shelter_address_edit);
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
}

