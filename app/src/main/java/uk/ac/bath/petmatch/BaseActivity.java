package uk.ac.bath.petmatch;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
public abstract class BaseActivity<H extends OrmLiteSqliteOpenHelper> extends AppCompatActivity {

    protected volatile DbHelper db;

    protected volatile LoginService loginService;

    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;





    /**
     * Get a helper for this action.
     */
    public DbHelper getHelper() {
        return db;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDbHelper();
        createLoginService();
    }

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