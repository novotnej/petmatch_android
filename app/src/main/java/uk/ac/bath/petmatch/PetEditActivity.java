package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.bath.petmatch.Database.Pet;

public class PetEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_edit);

        Intent intent = getIntent();
        Pet petClicked = (Pet)intent.getSerializableExtra("The Pet");

        EditText editTitle = (EditText)findViewById(R.id.petTitle);
        editTitle.setText(petClicked.getTitle(), TextView.BufferType.EDITABLE);

        EditText editDesc = (EditText)findViewById(R.id.petDesc);
        editDesc.setText(petClicked.getDescription(), TextView.BufferType.EDITABLE);
    }

}
