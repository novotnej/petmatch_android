package uk.ac.bath.petmatch.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import uk.ac.bath.petmatch.R;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "petmatch.db";
    // any time you make changes to your database objects, you may have to increase the database version

    private static final int DATABASE_VERSION = 1;

    public PetDao pets;
    public PetBreedDao petBreeds;
    public FavoritePetDao favoritePets;
    public ShelterDao shelters;
    public UserDao users;
    public UserPropertiesDao userProperties;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        initiateDAOs();
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        Log.i("DBHelper", "starting onCreate");
        createTables(connectionSource);
        generateSampleData();
        Log.i("DBHelper", "finished onCreate");
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) { //not greater than, because if by some mistake we downgrade the version, it should still regenerate
            dropTables(connectionSource);
            createTables(connectionSource);
            generateSampleData();
        }
    }

    public void generateSampleData() {
        Log.i("DBHelper", "generating sample data");
        /*Country countryGhana = new Country("Ghana", "GH₵");

        CountryDao countryDao = getCountryDao();
        countryDao.create(countryGhana);*/
    }

    public void dropTables(ConnectionSource connectionSource) {
        try {
            Log.i(DbHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Pet.class, true);
            TableUtils.dropTable(connectionSource, FavoritePet.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Shelter.class, true);
            TableUtils.dropTable(connectionSource, UserProperties.class, true);
            TableUtils.dropTable(connectionSource, PetBreed.class, true);

            // after we drop the old databases, we create the new ones

        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public void createTables(ConnectionSource connectionSource) {
        try {
            Log.i(DbHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Pet.class);
            TableUtils.createTable(connectionSource, PetBreed.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, UserProperties.class);
            TableUtils.createTable(connectionSource, Shelter.class);
            TableUtils.createTable(connectionSource, FavoritePet.class);

        } catch (SQLException e) {
            Log.e(DbHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    public void initiateDAOs() {
        pets = getPetsDao();
        petBreeds = getPetBreedsDao();
        users = getUsersDao();
        userProperties = getUserPropertiesDao();
        shelters = getSheltersDao();
        favoritePets = getFavoritePetsDao();
    }

    private PetDao getPetsDao() {
        try {
            Dao<Pet, String> dao = this.getDao(Pet.class);
            return new PetDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for pets");
        }
    }

    private PetBreedDao getPetBreedsDao() {
        try {
            Dao<PetBreed, String> dao = this.getDao(PetBreed.class);
            return new PetBreedDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for pet breeds");
        }
    }

    private FavoritePetDao getFavoritePetsDao() {
        try {
            Dao<FavoritePet, String> dao = this.getDao(FavoritePet.class);
            return new FavoritePetDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for favorite pets");
        }
    }

    private UserDao getUsersDao() {
        try {
            Dao<User, String> dao = this.getDao(User.class);
            return new UserDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for users");
        }
    }

    private UserPropertiesDao getUserPropertiesDao() {
        try {
            Dao<UserProperties, String> dao = this.getDao(UserProperties.class);
            return new UserPropertiesDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for user properties");
        }
    }

    private ShelterDao getSheltersDao() {
        try {
            Dao<Shelter, String> dao = this.getDao(Shelter.class);
            return new ShelterDao(dao);
        } catch (SQLException var4) {
            throw new RuntimeException("Could not create RuntimeExceptionDao for shelters");
        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        pets = null;
        petBreeds = null;
        favoritePets = null;
        users = null;
        userProperties = null;
        shelters = null;
    }
}