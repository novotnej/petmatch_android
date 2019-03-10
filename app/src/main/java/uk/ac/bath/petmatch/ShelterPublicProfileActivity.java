package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import java.util.Objects;

public class ShelterPublicProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_public_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");
        EditText shelterAddress = (EditText)findViewById(R.id.shelter_address);
        EditText shelterEmail = (EditText)findViewById(R.id.shelter_email);
        EditText shelterPhone = (EditText)findViewById(R.id.shelter_phone);

        shelterAddress.setInputType(InputType.TYPE_NULL);
        shelterEmail.setInputType(InputType.TYPE_NULL);
        shelterPhone.setInputType(InputType.TYPE_NULL);
    }
}
