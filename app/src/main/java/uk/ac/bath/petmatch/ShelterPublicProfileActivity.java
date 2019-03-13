package uk.ac.bath.petmatch;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.User;

public class ShelterPublicProfileActivity extends BaseActivity implements OnMapReadyCallback {

    private Shelter currentShelter;
    private User currentUser;
    private KeyListener listenerAddress;
    private KeyListener listenerEmail;
    private KeyListener listenerCharityNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_public_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");

        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title);
        TextView shelterIntroduction = (TextView) findViewById(R.id.shelter_introduction);
        EditText shelterAddress = (EditText)findViewById(R.id.shelter_address);
        EditText shelterEmail = (EditText)findViewById(R.id.shelter_email);
        EditText shelterCharityNumber = (EditText)findViewById(R.id.shelter_charity_no);

        // assign listeners to the variables to be able to make the fields editable later.
        listenerAddress = shelterAddress.getKeyListener();
        listenerEmail = shelterEmail.getKeyListener();
        listenerCharityNumber = shelterCharityNumber.getKeyListener();

        // set the listeners to null making the fields uneditable.
        shelterAddress.setKeyListener(null);
        shelterEmail.setKeyListener(null);
        shelterCharityNumber.setKeyListener(null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shelter_map);
        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        params.height = 850;
        mapFragment.getView().setLayoutParams(params);

        // get current shelter and set all fields to match it.
        boolean isUserLoggedIn = loginService.isUserLoggedIn();
        if(isUserLoggedIn && (currentShelter = loginService.getLoggedInUser().getShelter()) != null) {

            mapFragment.getMapAsync(this);

            shelterTitle.setText(currentShelter.getTitle());
            shelterIntroduction.setText(currentShelter.getDescription());
            shelterAddress.setText(currentShelter.getAddress());
            shelterEmail.setText(currentShelter.getEmail());
            shelterCharityNumber.setText(currentShelter.getCharityNumber());
        }
    }

    public KeyListener getListenerAddress() {
        return listenerAddress;
    }

    public KeyListener getListenerEmail() {
        return listenerEmail;
    }

    public KeyListener getListenerCharityNumber() {
        return listenerCharityNumber;
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker to the shelter's location.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        List<String> gps_coordinates = Arrays.asList(currentShelter.getGps().split(",[ ]*"));

        double latitude = Double.parseDouble(gps_coordinates.get(0));
        double longitude = Double.parseDouble(gps_coordinates.get(1));

        // Add a marker for the given shelter's coordinates and move the map's camera to the same location.
        LatLng shelter_location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(shelter_location)
                .title("Shelter"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shelter_location, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
