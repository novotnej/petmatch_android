package uk.ac.bath.petmatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;

public class PetProfileActivity extends BaseActivity {
    List<Pet> dummyPetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        //Get the Intent that started this activity - useful for taking stuff from other ones if needed
        Intent intent = getIntent();
        final String petClickedId = (String)intent.getStringExtra("The Pet");
        PetDao pets = getHelper().pets;
        final Pet petClicked = pets.queryForId(petClickedId);



        TextView petTitle = (TextView)findViewById(R.id.petTitle);
        petTitle.setText(petClicked.getTitle(), TextView.BufferType.EDITABLE);

        TextView petDesc = (TextView)findViewById(R.id.petDesc);
        petDesc.setText(petClicked.getDescription(), TextView.BufferType.EDITABLE);

        final Button button = findViewById(R.id.editButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //go to pet edit activity
                Intent petEditIntent = new Intent(getApplicationContext(), PetEditActivity.class);
                petEditIntent.putExtra("The Pet", petClickedId);
                startActivity(petEditIntent);
            }

        });

        final Button bbutton = findViewById(R.id.backButton);
        bbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //go to pet edit activity
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }

        });
    }
}
