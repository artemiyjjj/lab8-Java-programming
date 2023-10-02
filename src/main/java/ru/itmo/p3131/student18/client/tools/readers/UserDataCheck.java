package ru.itmo.p3131.student18.client.tools.readers;

import ru.itmo.p3131.student18.interim.exeptions.ObjectFieldsValueException;

public class UserDataCheck {
    private final int minLoginLength = 6;
    private final int minPasswordLength = 6;

    public String readLogin(String login) throws ObjectFieldsValueException {
        if (login.length() < minLoginLength) {
            throw new ObjectFieldsValueException("Login length can not be less than " + minLoginLength + ".");
        } else {
            return login;
        }
    }

    public String readNewPassword(String password, String passwordAgain) throws ObjectFieldsValueException {
        readPassword(password);
        if (password.equals(passwordAgain)) {
            return password;
        }
        else throw new ObjectFieldsValueException("Passwords are not same.");
    }

    public String readPassword(String password) throws ObjectFieldsValueException {
        if (password.length() < minPasswordLength) {
           throw new ObjectFieldsValueException("The length of password can not be less than " + minPasswordLength + ".");
        } else {
            return password;
        }
    }
}
