package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        if(currentShelter != null) {

//            System.out.println(shelterIntroduction.getText().toString());
//            System.out.println(shelterAddress.getText().toString());
//            System.out.println(shelterEmail.getText().toString());
//            System.out.println(shelterCharityNumber.getText().toString());
//            System.out.println(shelterPhoneNumber.getText().toString());

            currentShelter.setDescription(shelterIntroduction.getText().toString());
            currentShelter.setAddress(shelterAddress.getText().toString());
            currentShelter.setEmail(shelterEmail.getText().toString());
            currentShelter.setCharityNumber(shelterCharityNumber.getText().toString());
            currentShelter.setPhoneNumber(shelterPhoneNumber.getText().toString());
        }
    }
}

