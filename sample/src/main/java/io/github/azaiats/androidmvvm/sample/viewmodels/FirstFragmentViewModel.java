package io.github.azaiats.androidmvvm.sample.viewmodels;

import android.databinding.Bindable;

import io.github.azaiats.androidmvvm.core.common.BaseViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class FirstFragmentViewModel extends BaseViewModel {

    @Bindable
    public String getText() {
        return "First fragment - more examples will be added soon";
    }
}
