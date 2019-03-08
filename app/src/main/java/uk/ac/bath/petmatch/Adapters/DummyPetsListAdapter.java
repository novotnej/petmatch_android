package uk.ac.bath.petmatch.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.R;

public class DummyPetsListAdapter extends ArrayAdapter<Pet> {

    private LayoutInflater mInflater;
    private ArrayList<Pet> pets;
    private int mViewResourceId;

    public DummyPetsListAdapter(Context context, int textViewResourceId, ArrayList<Pet> pets) {
        super(context, textViewResourceId, pets);
        this.pets = pets;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public Pet getItem(int position){
        return pets.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Pet pet = pets.get(position);

        if (pet != null) {
            TextView petDetailsTextView = (TextView) convertView.findViewById(R.id.dummyPetDetails);
            //for some reason it keeps adding a pet with all null values but I can't find where
            if (pet.getShelter() != null){
                Log.d("dummy Pets listview", "title, description, shelter title"  + pet.getTitle() + pet.getDescription() + pet.getShelter().getTitle());
                petDetailsTextView.setText(pet.getTitle() + pet.getBreed().getTitle() + pet.getShelter().getTitle());
            }

        }

        return convertView;
    }
}