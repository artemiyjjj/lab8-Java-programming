package ru.itmo.p3131.student18.client.tools;

import ru.itmo.p3131.student18.client.tools.readers.UserDataCheck;
import ru.itmo.p3131.student18.interim.exeptions.ObjectFieldsValueException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserAction {
    private User user;
    private final UserDataCheck userDataChecker = new UserDataCheck();
    private String insertedLogin;
    private String insertedEncryptedPassword;

    public void logUser(String login, String password) throws ObjectFieldsValueException {
        insertedLogin = userDataChecker.readLogin(login);
        insertedEncryptedPassword = getPasswordHash(userDataChecker.readPassword(password));
    }

    /**
     *
     * @throws SQLException if inserting user data into Database failed.
     */
    public void registerUser(String login, String password, String passwordAgain) throws ObjectFieldsValueException {
        insertedLogin = userDataChecker.readLogin(login);
        insertedEncryptedPassword = getPasswordHash(userDataChecker.readNewPassword(password, passwordAgain));
    }

    public void completeAuthorization() {
        user = new User(insertedLogin);
    }

    public User getUser() {
        return user;
    }

    public String getInsertedLogin() {
        return insertedLogin;
    }

    public String getInsertedEncryptedPassword() {
        return insertedEncryptedPassword;
    }

    private String getPasswordHash(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(StandardCharsets.UTF_8.encode(password));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
