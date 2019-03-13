package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nameField = (EditText) findViewById(R.id.nameText);
                String name = nameField.getText().toString();

                final EditText emailField = (EditText) findViewById(R.id.descText);
                String email = emailField.getText().toString();

                ShelterDao shelters = getHelper().shelters;
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
