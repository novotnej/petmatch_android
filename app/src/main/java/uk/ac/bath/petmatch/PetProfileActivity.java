package uk.ac.bath.petmatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.bath.petmatch.Database.FavoritePetDao;
import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.Database.PetDao;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserDao;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

public class PetProfileActivity extends BaseActivity {
    List<Pet> dummyPetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        //Get the Intent that started this activity - useful for taking stuff from other ones if needed
        Intent intent = getIntent();
        final String petClickedId = (String)intent.getStringExtra("The Pet");
        PetDao pets = getHelper().pets;
        final Pet petClicked = pets.queryForId(petClickedId);



        TextView petTitle = (TextView)findViewById(R.id.petTitle);
        petTitle.setText(petClicked.getTitle(), TextView.BufferType.EDITABLE);

        TextView petDesc = (TextView)findViewById(R.id.petDesc);
        petDesc.setText(petClicked.getDescription(), TextView.BufferType.EDITABLE);

        TextView webLink = (TextView)findViewById(R.id.linkText);
        webLink.setText(petClicked.getShelter().getTitle(), TextView.BufferType.EDITABLE);
        webLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent shelterProfIntent = new Intent(getApplicationContext(), ShelterPublicProfileActivity.class);
                shelterProfIntent.putExtra("shelter_id", petClicked.getShelter().getId());
                startActivity(shelterProfIntent);
            }
        });

        ImageView petImage = (ImageView)findViewById(R.id.petImage);
        String id = petClicked.getImage();
        int resID;
        try {
            resID = (int) R.mipmap.class.getField(id).get(null);
        } catch (Exception e) {
            Log.e("PetGridAdapter", e.getMessage());
            resID = R.mipmap.paws;
        }
        petImage.setImageResource(resID);
        //petImageView.setImageResource(R.mipmap.dumbledore);



        final Button button = findViewById(R.id.editButton);
        if (loginService.getLoggedInUser() != null){
            if (loginService.getLoggedInUser().getShelter() !=null){
                if(loginService.getLoggedInUser().getShelter().getTitle().equals(petClicked.getShelter().getTitle())){
                    button.setVisibility(View.VISIBLE);
                }
                else{
                    button.setVisibility(View.GONE);
                }

            }
            else{
                button.setVisibility(View.GONE);
            }
        }
        else{
            button.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //go to pet edit activity
                Intent petEditIntent = new Intent(getApplicationContext(), PetEditActivity.class);
                petEditIntent.putExtra("The Pet", petClickedId);
                startActivity(petEditIntent);
            }

        });

        final Button addToFavButton = findViewById(R.id.addToFavouritesButton);
        final Button removeFromFavButton = findViewById(R.id.removeFromFavouritesButton);
        final FavoritePetDao allFavPets = getHelper().favoritePets;
        if (loginService.getLoggedInUser() != null){
            User user = loginService.getLoggedInUser();
            if (allFavPets.isFavourite(user, petClicked)) {
                removeFromFavButton.setVisibility(View.VISIBLE);
                addToFavButton.setVisibility(View.GONE);
            }
            else {
                addToFavButton.setVisibility(View.VISIBLE);
                removeFromFavButton.setVisibility(View.GONE);
            }
        }
        else{
            addToFavButton.setVisibility(View.GONE);
            removeFromFavButton.setVisibility(View.GONE);
        }
        addToFavButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // add pet to the users favourite pet list
                if (loginService.getLoggedInUser() != null) {
                    User user = loginService.getLoggedInUser();
                    //FavoritePetDao allFavPets = getHelper().favoritePets;
                    allFavPets.addToFavourites(user, petClicked);
                    ToastAdapter.toastMessage(getApplicationContext(), "Added to Favourites");
                }
            }

        });
        removeFromFavButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // add pet to the users favourite pet list
                if (loginService.getLoggedInUser() != null) {
                    User user = loginService.getLoggedInUser();
                    //FavoritePetDao allFavPets = getHelper().favoritePets;
                    allFavPets.removeFromFavourites(user, petClicked);
                    ToastAdapter.toastMessage(getApplicationContext(), "Removed from Favourites");
                }
            }

        });
    }
}
