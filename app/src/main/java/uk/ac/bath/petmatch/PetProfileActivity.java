package uk.ac.bath.petmatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.ac.bath.petmatch.Database.Pet;

public class PetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        //Get the Intent that started this activity - useful for taking stuff from other ones if needed
        Intent intent = getIntent();
        Pet petClicked = (Pet)intent.getSerializableExtra("The Pet");

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
                startActivity(petEditIntent);
            }

        });
    }
}
