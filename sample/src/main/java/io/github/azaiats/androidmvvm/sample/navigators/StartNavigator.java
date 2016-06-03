package io.github.azaiats.androidmvvm.sample.navigators;

import android.app.Activity;
import android.content.Intent;

import io.github.azaiats.androidmvvm.navigation.common.Navigator;
import io.github.azaiats.androidmvvm.sample.activities.MainActivity;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class StartNavigator implements Navigator {

    private Activity activity;

    /**
     * Create StartNavigation
     *
     * @param activity the activity for navigation delegation
     */
    public StartNavigator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Start main app activity
     */
    public void navigateToMain() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }
}
