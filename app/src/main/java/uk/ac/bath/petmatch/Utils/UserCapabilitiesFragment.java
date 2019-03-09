package uk.ac.bath.petmatch.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import uk.ac.bath.petmatch.Database.UserProperties;
import uk.ac.bath.petmatch.R;
import uk.ac.bath.petmatch.UserCapabilitiesActivity;

public class UserCapabilitiesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        // set preferences to current user properties in the database.
        Activity activity = getActivity();
        if(activity instanceof UserCapabilitiesActivity) {
            UserProperties userProperties = ((UserCapabilitiesActivity)activity).getUserProperties();
            editor.putBoolean("pref_kids", userProperties.hasKids());
            editor.putBoolean("pref_cat_allergy", userProperties.hasCatAllergies());
            editor.putBoolean("pref_dog_allergy", userProperties.hasDogAllergies());
            editor.putBoolean("pref_garden", userProperties.getGreenAreas());
            editor.putBoolean("pref_exercise", userProperties.getFreeTime());
        }
        editor.apply();
        addPreferencesFromResource(R.xml.user_capabilities_preferences);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // update user properties in database
        Activity activity = getActivity();
        if(activity instanceof UserCapabilitiesActivity) {
            ((UserCapabilitiesActivity)activity).updateUserCapabilities();
        }
    }
}