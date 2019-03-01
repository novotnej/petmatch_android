package uk.ac.bath.petmatch.Utils;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import uk.ac.bath.petmatch.R;

public class UserCapabilitiesFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.user_capabilities_preferences);
    }
}