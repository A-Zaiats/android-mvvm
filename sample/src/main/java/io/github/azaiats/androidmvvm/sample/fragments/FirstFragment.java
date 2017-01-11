package io.github.azaiats.androidmvvm.sample.fragments;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.MvvmFragment;
import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.common.BindingConfig;
import io.github.azaiats.androidmvvm.databinding.FragmentFirstBinding;
import io.github.azaiats.androidmvvm.sample.viewmodels.FirstFragmentViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class FirstFragment extends MvvmFragment<FragmentFirstBinding, FirstFragmentViewModel> {

    @NonNull
    @Override
    public FirstFragmentViewModel createViewModel() {
        return new FirstFragmentViewModel();
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_first);
    }
}
