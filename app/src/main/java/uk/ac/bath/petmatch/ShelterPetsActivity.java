package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Objects;

import uk.ac.bath.petmatch.Adapters.PetGridAdapter;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

public class ShelterPetsActivity extends BaseActivity {

    private Shelter currentShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.currentShelter = currentShelter;
        setContentView(R.layout.pet_grid_only);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Pets");

        Intent intent = getIntent();
        String id = intent.getStringExtra("shelter_id");
        ShelterDao shelterDao = db.shelters;
        currentShelter = shelterDao.queryForId(id);

        loadSheltersPetsGrid();
    }

    private void loadSheltersPetsGrid() {

        PetDao petDao = getHelper().pets;
        ArrayList<Pet> pets = petDao.getDummy();
        ArrayList<Pet> shelterPets = new ArrayList<Pet>();

        for (int i=0; i<pets.size(); i++) {
            if (pets.get(i).getShelter().getId().equals(currentShelter.getId())) {
                shelterPets.add(pets.get(i));
            }
        }

        if (shelterPets.size() == 0) {
            ToastAdapter.toastMessage(this, "This shelter has no pets");
        } else {
            this.createPetGridView((GridView) findViewById(R.id.pet_grid_layout), shelterPets);
        }

    }

    private void createPetGridView(final GridView gridView, ArrayList<Pet> pets) {
        PetGridAdapter adapter = new PetGridAdapter(this, R.layout.grid_item, pets);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Pet petClicked = (Pet) adapterView.getItemAtPosition(position);
                petClicked = getHelper().pets.queryForId(petClicked.getId());
                Log.i("Clicked pet", "" + petClicked.getTitle());
                Intent petProfIntent = new Intent(getApplicationContext(), PetProfileActivity.class);
                petProfIntent.putExtra("The Pet", petClicked.getId());
                startActivity(petProfIntent);
            }
        });
    }
}
