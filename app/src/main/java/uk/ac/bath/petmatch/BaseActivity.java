package uk.ac.bath.petmatch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import uk.ac.bath.petmatch.Database.DbHelper;
import uk.ac.bath.petmatch.Services.LoginService;
import uk.ac.bath.petmatch.Utils.ToastAdapter;

/**
 * Base class to use for activities in Android.
 */
public abstract class BaseActivity<H extends OrmLiteSqliteOpenHelper> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected volatile DbHelper db;

    protected volatile LoginService loginService;

    /**
     * Get a helper for this action.
     */
    public DbHelper getHelper() {
        return db;
    }

    protected void onCreate(Bundle savedInstanceState, int layoutResID) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        createDbHelper();
        createLoginService();
        generateLoggedUserView();

        setUpMenu();
    }

    protected void setUpMenu() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View sidebarView = mInflater.inflate(R.id.cont, null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

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
     * MENU
     */


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

    public void menuPetAddClicked(View view) {
        Intent petAddIntent = new Intent(getApplicationContext(), PetAddActivity.class);
        startActivity(petAddIntent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void menuShelterProfileClicked(View view) {
        Intent startShelterProfileIntent = new Intent(getApplicationContext(),
                ShelterProfileActivity.class);
        startActivity(startShelterProfileIntent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void menuUserCapabilitiesClicked(View view) {
        Intent startUserCapabilitiesIntent = new Intent(getApplicationContext(),
                UserCapabilitiesActivity.class);
        startActivity(startUserCapabilitiesIntent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public boolean onNavigationItemSelected(MenuItem var1) {
        Log.d("NAV_Click", var1.toString());
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * SERVICES
     */

    protected void createLoginService() {
        if (loginService == null) {
            loginService = new LoginService(db.users);
        }
    }

    protected void createDbHelper() {
        if (db == null) {
            db = getHelperInternal(this);
        }
    }

    /**
     * DATABASE
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseHelper(db);
    }

    /**
     * This is called internally by the class to populate the helper object instance. This should not be called directly
     * by client code unless you know what you are doing. Use {@link #getHelper()} to get a helper instance. If you are
     * managing your own helper creation, override this method to supply this activity with a helper instance.
     */
    protected DbHelper getHelperInternal(Context context) {
        @SuppressWarnings({ "unchecked", "deprecation" })
        DbHelper newHelper = (DbHelper) OpenHelperManager.getHelper(context, DbHelper.class);
        Log.i("OrmLiteBaseActivity", "got new helper from OpenHelperManager");
        return newHelper;
    }

    /**
     * Release the helper instance created in {@link #getHelperInternal(Context)}. You most likely will not need to call
     * this directly since {@link #onDestroy()} does it for you.
     */
    protected void releaseHelper(DbHelper helper) {
        OpenHelperManager.releaseHelper();
        Log.i("OrmLiteBaseActivity", "helper was released, set to null");
        this.db = null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(super.hashCode());
    }
}