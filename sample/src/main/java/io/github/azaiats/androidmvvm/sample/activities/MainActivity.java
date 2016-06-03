package io.github.azaiats.androidmvvm.sample.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.databinding.ActivityMainBinding;
import io.github.azaiats.androidmvvm.databinding.NavHeaderMainBinding;
import io.github.azaiats.androidmvvm.navigation.NavigatingMvvmActivity;
import io.github.azaiats.androidmvvm.sample.navigators.MainNavigator;
import io.github.azaiats.androidmvvm.sample.utils.AppPreferences;
import io.github.azaiats.androidmvvm.sample.viewmodels.MainViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/12/2016
 */
public class MainActivity extends NavigatingMvvmActivity<MainNavigator, ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = getBinding().toolbar;
        final DrawerLayout drawer = getBinding().drawerLayout;
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = getBinding().navView;
        final NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        headerBinding.setViewModel(getViewModel());
        headerBinding.executePendingBindings();
        navigationView.setNavigationItemSelectedListener(getNavigator());
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = getBinding().drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    @Override
    public MainNavigator getNavigator() {
        return new MainNavigator(this, getBinding().drawerLayout);
    }

    @NonNull
    @Override
    public MainViewModel createViewModel() {
        return new MainViewModel(new AppPreferences(this));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_main);
    }
}
