package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Adapters.PetGridAdapter;
import uk.ac.bath.petmatch.BaseActivity;
import uk.ac.bath.petmatch.Database.FavoritePet;
import uk.ac.bath.petmatch.Database.FavoritePetDao;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.R;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

public class FavouritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);



        loadFavouritePetGrid();
    }

    private void loadFavouritePetGrid() {
        User currentUser = loginService.getLoggedInUser();

        FavoritePetDao allFavPets = getHelper().favoritePets;
        ArrayList<FavoritePet> favPets = allFavPets.getFavourites(currentUser);
        ArrayList<Pet> pets = new ArrayList<Pet>();
        int noOfPets = favPets.size();
        for (int i=0; i<noOfPets; i++) {
            pets.add(favPets.get(i).getPet());
        }

        if (favPets.size() == 0) {
            ToastAdapter.toastMessage(this, "Database is empty");
        } else {
            this.createPetGridView((GridView) findViewById(R.id.pet_grid_layout), pets);
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
