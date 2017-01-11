package io.github.azaiats.androidmvvm.sample.activities;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.NavigatingMvvmActivity;
import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.common.BindingConfig;
import io.github.azaiats.androidmvvm.databinding.ActivityStartBinding;
import io.github.azaiats.androidmvvm.sample.navigators.StartNavigator;
import io.github.azaiats.androidmvvm.sample.utils.AppPreferences;
import io.github.azaiats.androidmvvm.sample.viewmodels.StartViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/12/2016
 */
public class StartActivity extends NavigatingMvvmActivity<StartNavigator, ActivityStartBinding, StartViewModel> {

    @NonNull
    @Override
    public StartViewModel createViewModel() {
        return new StartViewModel(new AppPreferences(this));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_start);
    }

    @NonNull
    @Override
    public StartNavigator getNavigator() {
        return new StartNavigator(this);
    }
}
