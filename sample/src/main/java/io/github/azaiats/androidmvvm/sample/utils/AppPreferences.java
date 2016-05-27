package io.github.azaiats.androidmvvm.sample.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class AppPreferences {

    private static final String NAME_KEY = "NAME_KEY";

    private final SharedPreferences preferences;

    public AppPreferences(final Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences("io.github.azaiats.androidmvvm.sample.AppPreferences", Context.MODE_PRIVATE);
    }

    public void setName(String name) {
        preferences.edit().putString(NAME_KEY, name).apply();
    }

    public String getName() {
        return preferences.getString(NAME_KEY, null);
    }
}
