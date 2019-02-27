package uk.ac.bath.petmatch.Services;

import android.os.Build;
import android.support.annotation.RequiresApi;

import uk.ac.bath.petmatch.Database.Shelter;
import uk.ac.bath.petmatch.Database.User;
import uk.ac.bath.petmatch.Database.UserDao;
import uk.ac.bath.petmatch.Utils.PasswordUtils;

public final class LoginService {
    private static User loggedInUser;
    private UserDao usersDao;

    public LoginService(UserDao usersDao) {
        this.usersDao = usersDao;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(String name, String email, String password, Shelter shelter) {
        User newUser = new User(name, email, PasswordUtils.generateSecurePassword(password, PasswordUtils.getSalt(40)), shelter);
        User foundUser = usersDao.findByEmail(email);
        if (foundUser == null) {
            usersDao.create(newUser);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User login(String email, String password) {
        User user = this.usersDao.findByEmail(email);
        if (user != null) {
            if (PasswordUtils.verifyUserPassword(password, user.getPassword(), PasswordUtils.getSalt(40))) {
                loggedInUser = user;
                return user;
            }
        }
        return null;
    }

    public void logout() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

}
