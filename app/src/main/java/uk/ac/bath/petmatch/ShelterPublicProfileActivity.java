package uk.ac.bath.petmatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.ShelterDao;

public class ShelterPublicProfileActivity extends BaseActivity implements OnMapReadyCallback {

    private Shelter currentShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_public_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Shelter Profile");

        TextView shelterTitle = (TextView) findViewById(R.id.shelter_profile_title_edit);
        TextView shelterIntroduction = (TextView) findViewById(R.id.shelter_introduction_edit);
        TextView shelterAddress = (TextView)findViewById(R.id.shelter_address);
        TextView shelterEmail = (TextView)findViewById(R.id.shelter_email);
        TextView shelterCharityNumber = (TextView)findViewById(R.id.shelter_charity_no);
        TextView shelterPhoneNumber = (TextView)findViewById(R.id.shelter_phone);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shelter_map);
        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        params.height = 850;
        mapFragment.setHasOptionsMenu(true);
        mapFragment.setMenuVisibility(true);
        mapFragment.getView().setLayoutParams(params);
        mapFragment.setMenuVisibility(true);
        Intent intent = getIntent();
        String id = intent.getStringExtra("shelter_id");
        ShelterDao shelterDao = db.shelters;
        currentShelter = shelterDao.queryForId(id);

        mapFragment.getMapAsync(this);

        shelterTitle.setText(currentShelter.getTitle());
        shelterIntroduction.setText(currentShelter.getDescription());
        shelterAddress.setText(currentShelter.getAddress());
        shelterEmail.setText(currentShelter.getEmail());
        shelterEmail.setOnClickListener(emailClick);
        shelterCharityNumber.setText(currentShelter.getCharityNumber());
        shelterPhoneNumber.setText(currentShelter.getPhoneNumber());
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

    public final EditText.OnClickListener emailClick = new EditText.OnClickListener() {


        @Override
        public void onClick(View v) {
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
            mailIntent.setData(Uri.parse("mailto:"));
            mailIntent.putExtra(Intent.EXTRA_EMAIL, currentShelter.getEmail());
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            mailIntent.putExtra(Intent.EXTRA_TEXT, "");
            if (mailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mailIntent);
            } else {
                // no e-mail app installed
            }
        }
    };
}
