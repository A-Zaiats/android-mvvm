package io.github.azaiats.androidmvvm.sample.viewmodels;

import io.github.azaiats.androidmvvm.navigation.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.sample.navigators.MainNavigator;
import io.github.azaiats.androidmvvm.sample.utils.AppPreferences;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class MainViewModel extends NavigatingViewModel<MainNavigator> {

    private String name;
    private final AppPreferences preferences;

    /**
     * Create MainViewModel with preferences
     *
     * @param preferences the AppPreferences
     */
    public MainViewModel(AppPreferences preferences) {
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        name = preferences.getName();
    }
}
