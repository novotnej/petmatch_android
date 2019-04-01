package uk.ac.bath.petmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        Button loginButton = (Button) findViewById(R.id.welcome_login_button);
        Button justBrowseButton = (Button) findViewById(R.id.welcome_browse_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginActivity = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(loginActivity);
            }
        });

        justBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainActivity = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}
