package uk.ac.bath.petmatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Adapters.DummyPetsListAdapter;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Utils.ToastAdapter;
import uk.ac.bath.petmatch.Utils.UIUtils;

public class MainActivity extends BaseActivity {

    ArrayList<Pet> dummyPetList;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petAddIntent = new Intent(getApplicationContext(), PetAddActivity.class);
                startActivity(petAddIntent);
            }
        });

        loadDummyPetList();

    }

    private void loadDummyPetList() {
        PetDao pets = getHelper().pets;
        dummyPetList = pets.getDummy();
        if (dummyPetList.size() == 0) {
            ToastAdapter.toastMessage(this, "Databse is empty");
        } else {
            this.createDummyPetsListView((ListView) findViewById(R.id.dummy_pets_list), dummyPetList);
        }
    }

    private void createDummyPetsListView(final ListView listView, ArrayList<Pet> assets) {
        DummyPetsListAdapter adapter = new DummyPetsListAdapter(this, R.layout.list_adapter_dummy_pets, assets);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Pet petClicked = (Pet) adapterView.getItemAtPosition(position);
                petClicked = getHelper().pets.queryForId(petClicked.getId());
                Log.i("Clicked pet", "" + petClicked.getTitle());
                //TODO - perhaps open a new activity or something
                Intent petProfIntent = new Intent(getApplicationContext(), PetProfileActivity.class);
                startActivity(petProfIntent);
            }
        });
        UIUtils.setListViewHeightBasedOnItems(listView);
    }

}
