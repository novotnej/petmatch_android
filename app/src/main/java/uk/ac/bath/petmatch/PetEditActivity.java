package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetBreed;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

public class PetEditActivity extends BaseActivity {

    PetBreed filterPetBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_edit);
        //setTitle("Edit Pet");

        Intent intent = getIntent();
        final String petClickedId = (String)intent.getStringExtra("The Pet");
        final PetDao pets = getHelper().pets;
        final Pet petClicked = pets.queryForId(petClickedId);

        final EditText editTitle = (EditText)findViewById(R.id.petTitle);
        editTitle.setText(petClicked.getTitle(), TextView.BufferType.EDITABLE);

        final EditText editDesc = (EditText)findViewById(R.id.petDesc);
        editDesc.setText(petClicked.getDescription(), TextView.BufferType.EDITABLE);

        ImageView petImage = (ImageView)findViewById(R.id.petImage);
        String id = petClicked.getImage();
        int resID;
        try {
            resID = (int) R.mipmap.class.getField(id).get(null);
        } catch (Exception e) {
            Log.e("PetGridAdapter", e.getMessage());
            resID = R.mipmap.dumbledore;
        }
        petImage.setImageResource(resID);
        //petImageView.setImageResource(R.mipmap.dumbledore);

        Spinner spinner = (Spinner) findViewById(R.id.breeds_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout

        //Get list of breeds in a given breed type
        String[] breeds = getHelper().petBreeds.getArrayForType();
        final String[] spinnerBreeds = new String[breeds.length +1];
        for (int i = 0; i < breeds.length; i++) {
            spinnerBreeds[i] = breeds[i];
        }
        spinnerBreeds[breeds.length] = petClicked.getBreed().getTitle(); //add an "empty" choice option

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
                petClicked.setBreed(filterPetBreed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Button button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //go to pet edit activity
                if (petClicked.getBreed() == null){
                    ToastAdapter.toastMessage(getApplicationContext(), "SELECT A BREED M8");
                }
                else{
                    petClicked.setTitle(editTitle.getText().toString());
                    petClicked.setDescription(editDesc.getText().toString());
                    pets.update(petClicked);
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }

            }

        });
    }

}
