package ru.itmo.p3131.student18.client.gui;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Translator {
    private static final Map<String, Locale> locales = new HashMap<>();
    static {
        locales.put("English", new Locale("en"));
        locales.put("Русский", new Locale("ru"));
        locales.put("Polski", new Locale("pl"));
        locales.put("Eesti", new Locale("ee"));
    }
    private static Locale currLocale = getLocale("English");

    public static Locale getLocale(String shortName) {
        return locales.get(shortName);
    }

    public static void setLocale(String localeName) {
        currLocale = getLocale(localeName);
    }

    public static Locale currentLocale() {
        return currLocale;
    }
}
