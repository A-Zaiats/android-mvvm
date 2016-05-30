package io.github.azaiats.androidmvvm.sample.viewmodels;

import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import io.github.azaiats.androidmvvm.BR;
import io.github.azaiats.androidmvvm.core.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.sample.navigators.StartNavigator;
import io.github.azaiats.androidmvvm.sample.utils.AppPreferences;

/**
 * @author Andrei Zaiats
 * @since 05/12/2016
 */
public class StartViewModel extends NavigatingViewModel<StartNavigator> {

    private String name;
    private final AppPreferences preferences;

    /**
     * Create StartViewModel with preferences
     *
     * @param preferences the AppPreferences
     */
    public StartViewModel(AppPreferences preferences) {
        this.preferences = preferences;
    }

    @Bindable
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * @param name an entered name
     */
    public void setName(String name) {
        if (TextUtils.equals(this.name, name)) return;

        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    /**
     * Clear current name.
     *
     * @param view an interacted view
     */
    public void onNameClear(View view) {
        setName(null);
    }

    /**
     * Start app.
     *
     * @param view an interacted view
     */
    public void onStartAppRequested(View view) {
        preferences.setName(name);
        if (navigator != null) {
            navigator.navigateToMain();
        }
    }
}
