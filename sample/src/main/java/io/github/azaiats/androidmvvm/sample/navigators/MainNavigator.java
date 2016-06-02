package io.github.azaiats.androidmvvm.sample.navigators;

import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.core.common.Navigator;
import io.github.azaiats.androidmvvm.sample.fragments.FirstFragment;
import io.github.azaiats.androidmvvm.sample.fragments.SecondFragment;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class MainNavigator implements Navigator, OnNavigationItemSelectedListener {

    private final FragmentActivity activity;
    private final DrawerLayout drawer;

    /**
     * Create MainNavigator
     *
     * @param activity the activity that contains fragments container
     * @param drawer the drawer contains {@link android.support.design.widget.NavigationView}
     */
    public MainNavigator(FragmentActivity activity, DrawerLayout drawer) {
        this.activity = activity;
        this.drawer = drawer;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_first) {
            fragment = new FirstFragment();
        } else if (id == R.id.nav_second) {
            fragment = new SecondFragment();
        }
        if (fragment == null) return false;

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        item.setChecked(true);
        activity.setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
