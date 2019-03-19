package uk.ac.bath.petmatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Adapters.PetGridAdapter;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetBreed;
import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Pet> pets;
    RadioGroup breedTypeRadioGroup;
    Spinner petBreedSpinner;
    SeekBar distanceSeekBar;
    TextView distanceValueTextView;
    String spinnerBreeds[];

    double userLocationLat, userLocationLon;
    int filterDistance = 5;
    String filterPetType;
    PetBreed filterPetBreed;
    public static final double DEFAULT_LOCATION_LAT = 51.389757;
    public static final double DEFAULT_LOCATION_LON = -2.363708;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (loginButton != null && loggedUserEmail != null && loggedUserName != null && logoutButton != null) {

            if (loginService.isUserLoggedIn()) {
                logoutButton.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                loggedUserName.setVisibility(View.VISIBLE);
                loggedUserName.setText(loginService.getLoggedInUser().getName());
                loggedUserEmail.setVisibility(View.VISIBLE);
                loggedUserEmail.setText(loginService.getLoggedInUser().getEmail());
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_user_capabilities).setVisible(true);
            } else {
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                loggedUserEmail.setVisibility(View.GONE);
                loggedUserName.setVisibility(View.GONE);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_user_capabilities).setVisible(false);
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
        setUpDistanceSeekBar();
        getUserLastKnownLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLastKnownLocation();
                } else {
                    this.userLocationLat = DEFAULT_LOCATION_LAT;
                    this.userLocationLon = DEFAULT_LOCATION_LON;
                    ToastAdapter.toastMessage(this, "We were unable to determine your location. Results may not be accurate.");
                }
                return;
            }
        }
    }

    //FIXME - not sure but does not actually return the current location, might be just because of the emulator
    protected void getUserLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                this.userLocationLat = location.getLatitude();
                this.userLocationLon = location.getLongitude();
            } else {
                this.userLocationLat = DEFAULT_LOCATION_LAT;
                this.userLocationLon = DEFAULT_LOCATION_LON;
            }
        }
    }

    protected void setUpDistanceSeekBar() {
        distanceSeekBar = findViewById(R.id.searchDistanceSeekbar);
        distanceValueTextView = findViewById(R.id.searchDistanceValue);

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               distanceValueTextView.setText("" + progress + " km");
               filterDistance = progress;
               reloadPetsList();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
            loadPetsByFilter(filterPetType, filterPetBreed.getId(), userLocationLat, userLocationLon, filterDistance);
        } else {
            loadPetsByFilter(filterPetType, null, userLocationLat, userLocationLon, filterDistance);
        }
    }

    private void loadPetsByFilter(String breedType, String petBreedId, double lat, double lon, int distance) {
        UserProperties userProperties = null;
        if (loginService.isUserLoggedIn()) {
            userProperties = getHelper().userProperties.loadByUser(loginService.getLoggedInUser());
        }
        pets = getHelper().pets.loadByFilter(getHelper().petBreeds, breedType, petBreedId, userProperties, lat, lon, distance);

        if (pets == null || pets.size() == 0) {
            ToastAdapter.toastMessage(this, "No pets fit your filter");
        }

        this.createPetGridView((GridView) findViewById(R.id.pet_grid_layout), pets);
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

        if (id == R.id.nav_pet_add) {
            Intent petAddIntent = new Intent(getApplicationContext(), PetAddActivity.class);
            startActivity(petAddIntent);
            // Handle the camera action
        } else if (id == R.id.nav_shelter_profile) {

            // handles settings
            Intent startShelterProfileIntent = new Intent(getApplicationContext(),
                    ShelterPublicProfileActivity.class);
            startActivity(startShelterProfileIntent);

        } else if (id == R.id.nav_user_capabilities) {

            Intent startUserCapabilitiesIntent = new Intent(getApplicationContext(),UserCapabilitiesActivity.class);
            startActivity(startUserCapabilitiesIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
