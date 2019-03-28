package uk.ac.bath.petmatch.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.bath.petmatch.Database.Pet;
import uk.ac.bath.petmatch.R;

public class PetGridAdapter extends ArrayAdapter<Pet> {

    private LayoutInflater inflater;
    private ArrayList<Pet> pets;
    private int viewResourceId;

    // Constructor
    public PetGridAdapter(Context context, int gridViewResourceId, ArrayList<Pet> pets) {
        super(context, gridViewResourceId, pets);
        this.pets = pets;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewResourceId = gridViewResourceId;

    }

    public Pet getItem(int position){
        return pets.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = this.inflater.inflate(this.viewResourceId, null);

        Pet pet = pets.get(position);
        ImageView petImageView;
        TextView petTitleView;
        if (pet != null) {
            petImageView = (ImageView) convertView.findViewById(R.id.gridImageView);
            petTitleView = (TextView) convertView.findViewById(R.id.petTitleView);
            String id = pet.getImage();
            int resID;
            try {
                resID = (int) R.mipmap.class.getField(id).get(null);
            } catch (Exception e) {
                Log.e("PetGridAdapter", e.getMessage());
                resID = R.mipmap.paws;
            }
            petImageView.setImageResource(resID);
            //petImageView.setImageResource(R.mipmap.dumbledore);
            petTitleView.setText(pet.getTitle());
            Log.d("dummy Pets listview", "title, description, shelter title"  + pet.getTitle() + pet.getDescription() + pet.getShelter().getTitle());
        }
        else {
            petImageView = null;
        }

        return petImageView;
    }

    public int getCount() {
        return pets.size();
    }

    public long getItemId(int position) {
        return 0;
    }

    // Keep all Images in array
    /*public Integer[] mThumbIds = {
            R.drawable.mushroom
    };*/
}