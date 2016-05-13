package io.github.azaiats.androidmvvm.sample.viewmodels;

import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import io.github.azaiats.androidmvvm.BR;
import io.github.azaiats.androidmvvm.core.common.BaseViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/12/2016
 */
public class MainViewModel extends BaseViewModel {

    private String name;

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
}
