package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Database.DbHelper;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetBreed;
import uk.ac.bath.petmatch.Database.PetBreedDao;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;

public class PetAddActivity extends BaseActivity {

    int RESULT_LOAD_IMAGE = 1;
    PetBreed petBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_add);



        Button buttonLoadImage = (Button) findViewById(R.id.imButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //doesn't do anything for now

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }


        });

        Spinner spinner = (Spinner) findViewById(R.id.breeds_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        //Get list of breeds in a given breed type
        final String[] breeds = getHelper().petBreeds.getArrayForType();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, breeds);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBreedTitle = breeds[position];
                //spinner contains titles of pet breeds. Find breed by title and use the PetBreed model in filter
                petBreed = getHelper().petBreeds.loadByTitle(selectedBreedTitle);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nameField = (EditText) findViewById(R.id.nameText);
                String name = nameField.getText().toString();

                final EditText descTextField = (EditText) findViewById(R.id.descText);
                String description = descTextField.getText().toString();

                Shelter shelter = loginService.getLoggedInUser().getShelter();

                Pet newPet = new Pet(name, description, shelter, petBreed);
                newPet.setImage(Pet.getRandomImage());
                getHelper().pets.create(newPet);

                //redirect back to main screen
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

                Log.d("import", "adding new pet");
            }
        });
    }


}
