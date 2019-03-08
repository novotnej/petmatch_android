package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Adapters.DummyPetsListAdapter;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetBreed;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Utils.ToastAdapter;
import uk.ac.bath.petmatch.Utils.UIUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Pet> dummyPetList;
    RadioGroup breedTypeRadioGroup;
    Spinner petBreedSpinner;
    String spinnerBreeds[];

    String filterPetType;
    PetBreed filterPetBreed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //loadDummyPetList();
        processSearchFilter();
        reloadPetsList();
        generateLoggedUserView();
    }

    /**
     * Switches login for log out button and vice-versa
     * sets textView with user's name when user is logged in
     */
    protected void generateLoggedUserView() {
        ImageView loginButton = (ImageView) findViewById(R.id.loginButton);
        ImageView logoutButton = (ImageView) findViewById(R.id.logoutButton);

        TextView loggedUserName = (TextView) findViewById(R.id.loggedUserName);
        TextView loggedUserEmail = (TextView) findViewById(R.id.loggedUserEmail);

        if (loginButton != null && loggedUserEmail != null && loggedUserName != null && logoutButton != null) {

            if (loginService.isUserLoggedIn()) {
                logoutButton.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                loggedUserName.setVisibility(View.VISIBLE);
                loggedUserName.setText(loginService.getLoggedInUser().getName());
                loggedUserEmail.setVisibility(View.VISIBLE);
                loggedUserEmail.setText(loginService.getLoggedInUser().getEmail());
            } else {
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                loggedUserEmail.setVisibility(View.GONE);
                loggedUserName.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Sets onclick actions for pet search filter and updates filter values
     * then reloads list of displayed pets
     */
    private void processSearchFilter() {
        breedTypeRadioGroup = findViewById(R.id.radioGroupPetType);
        breedTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId = breedTypeRadioGroup.getCheckedRadioButtonId();
                switch (selectedId) {
                    case R.id.radioButtonCatsAndDogs:
                        filterPetType = null;
                        Log.d("Pet breed type", "Both");
                        break;
                    case R.id.radioButtonCats:
                        filterPetType = PetBreed.TYPE_CAT;
                        Log.d("Pet breed type", "cats");
                        break;
                    case R.id.radioButtonDogs:
                        filterPetType = PetBreed.TYPE_DOG;
                        Log.d("Pet breed type", "dogs" +
                        "");
                        break;
                }
                reloadPetsList();
                setUpPetBreedSpinner();
            }
        });

        setUpPetBreedSpinner();
    }

    /**
     * Creates spinner (select breed) programmatically and its onchange action
     */
    protected void setUpPetBreedSpinner() {
        //Get list of breeds in a given breed type
        String[] breeds = getHelper().petBreeds.getArrayForType(filterPetType);
        spinnerBreeds = new String[breeds.length +1];
        for (int i = 0; i < breeds.length; i++) {
            spinnerBreeds[i] = breeds[i];
        }
        spinnerBreeds[breeds.length] = " --- ALL ---"; //add an "empty" choice option


        petBreedSpinner = (Spinner) findViewById(R.id.pet_breed_spinner);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerBreeds);
        petBreedSpinner.setAdapter(arrayAdapter);

        //Validate that the currently chosen filter value is a valid breed and is in the chosen breed type
        if (filterPetBreed != null) {
            int spinnerPosition = arrayAdapter.getPosition(filterPetBreed.getTitle());
            if (spinnerPosition == -1) { //if selected breed not in the options, choose "empty" value
                petBreedSpinner.setSelection(spinnerBreeds.length - 1);
            } else {
                petBreedSpinner.setSelection(spinnerPosition);
            }
        } else {
            petBreedSpinner.setSelection(spinnerBreeds.length - 1); //if no breed selected, choose "empty" valu
        }

        petBreedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBreedTitle = spinnerBreeds[position];
                //spinner contains titles of pet breeds. Find breed by title and use the PetBreed model in filter
                filterPetBreed = getHelper().petBreeds.loadByTitle(selectedBreedTitle);
                reloadPetsList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    protected void reloadPetsList() {
        if (filterPetBreed != null) {
            loadPetsByFilter(filterPetType, filterPetBreed.getId());
        } else {
            loadPetsByFilter(filterPetType, null);
        }
    }

    private void loadPetsByFilter(String breedType, String petBreedId) {
        UserProperties userProperties = null;
        if (loginService.isUserLoggedIn()) {
            userProperties = getHelper().userProperties.loadByUser(loginService.getLoggedInUser());
        }
        dummyPetList = getHelper().pets.loadByFilter(getHelper().petBreeds, breedType, petBreedId, userProperties);

        if (dummyPetList.size() == 0) {
            ToastAdapter.toastMessage(this, "No pets fit your filter");
        } else {
            this.createDummyPetsListView((ListView) findViewById(R.id.dummy_pets_list), dummyPetList);
        }
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        generateLoggedUserView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLoginButtonClicked(View view) {
        Log.d("Login", "attempt");
        /*if (loginService.login("user@petmatch.com", "1234") != null) {
            generateLoggedUserView();
        }*/
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onLogoutButtonClicked(View view) {
        loginService.logout();
        Log.d("Logout", "sd");
        generateLoggedUserView();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Log.d("navigClick", "" + id);

        if (id == R.id.nav_camera) {
            Intent petAddIntent = new Intent(getApplicationContext(), PetAddActivity.class);
            startActivity(petAddIntent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_shelter_profile) {

            // handles settings
            Intent startShelterProfileIntent = new Intent(getApplicationContext(),
                    ShelterProfileActivity.class);
            startActivity(startShelterProfileIntent);

        } else if (id == R.id.nav_settings) {

            // handles user capabilities
            Intent startUserCapabilitiesIntent = new Intent(getApplicationContext(),
                    UserCapabilitiesActivity.class);
            startActivity(startUserCapabilitiesIntent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
