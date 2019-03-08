package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;

public class PetEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_edit);

        Intent intent = getIntent();
        final String petClickedId = (String)intent.getStringExtra("The Pet");
        final PetDao pets = getHelper().pets;
        final Pet petClicked = pets.queryForId(petClickedId);

        final EditText editTitle = (EditText)findViewById(R.id.petTitle);
        editTitle.setText(petClicked.getTitle(), TextView.BufferType.EDITABLE);

        final EditText editDesc = (EditText)findViewById(R.id.petDesc);
        editDesc.setText(petClicked.getDescription(), TextView.BufferType.EDITABLE);

        final Button button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //go to pet edit activity
                petClicked.setTitle(editTitle.getText().toString());
                petClicked.setDescription(editDesc.getText().toString());
                pets.update(petClicked);
                Intent petProfIntent = new Intent(getApplicationContext(), PetProfileActivity.class);
                petProfIntent.putExtra("The Pet", petClickedId);
                startActivity(petProfIntent);
            }

        });
    }

}
