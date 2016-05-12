package io.github.azaiats.androidmvvm.sample.activities;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.core.MvvmActivity;
import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.databinding.ActivityMainBinding;
import io.github.azaiats.androidmvvm.sample.viewmodels.MainViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/12/2016
 */
public class MainActivity extends MvvmActivity<ActivityMainBinding, MainViewModel> {

    @NonNull
    @Override
    public MainViewModel createViewModel() {
        return new MainViewModel();
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_main);
    }
}
