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
    PetBreed filterPetBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_add);



        Button buttonLoadImage = (Button) findViewById(R.id.imButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }


        });

        Spinner spinner = (Spinner) findViewById(R.id.breeds_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ShelterDao shelters = getHelper().shelters;
        //Get list of breeds in a given breed type
        String[] breeds = getHelper().petBreeds.getArrayForType(PetBreed.TYPE_DOG);
        final String[] spinnerBreeds = new String[breeds.length +1];
        for (int i = 0; i < breeds.length; i++) {
            spinnerBreeds[i] = breeds[i];
        }
        spinnerBreeds[breeds.length] = " --- ALL ---"; //add an "empty" choice option

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerBreeds);
        spinner.setAdapter(arrayAdapter);


        //Validate that the currently chosen filter value is a valid breed and is in the chosen breed type
        if (filterPetBreed != null) {
            int spinnerPosition = arrayAdapter.getPosition(filterPetBreed.getTitle());
            if (spinnerPosition == -1) { //if selected breed not in the options, choose "empty" value
                spinner.setSelection(spinnerBreeds.length - 1);
            } else {
                spinner.setSelection(spinnerPosition);
            }
        } else {
            spinner.setSelection(spinnerBreeds.length - 1); //if no breed selected, choose "empty" valu
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBreedTitle = spinnerBreeds[position];
                //spinner contains titles of pet breeds. Find breed by title and use the PetBreed model in filter
                filterPetBreed = getHelper().petBreeds.loadByTitle(selectedBreedTitle);
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

                final EditText emailField = (EditText) findViewById(R.id.descText);
                String email = emailField.getText().toString();


                Shelter dummyBath = (Shelter) shelters.queryForId(0);

                PetBreed dummy = new PetBreed("dummy", PetBreed.TYPE_DOG, false, false, false, false, false);
                Pet newPet = new Pet(name, email, dummyBath, dummy);
                db.pets.create(newPet);

                Log.d("import", "adding new pet");


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


}
