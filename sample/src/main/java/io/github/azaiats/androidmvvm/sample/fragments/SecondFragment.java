package io.github.azaiats.androidmvvm.sample.fragments;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.R;
import io.github.azaiats.androidmvvm.core.MvvmFragment;
import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.databinding.FragmentFirstBinding;
import io.github.azaiats.androidmvvm.sample.viewmodels.SecondFragmentViewModel;

/**
 * @author Andrei Zaiats
 * @since 05/26/2016
 */
public class SecondFragment extends MvvmFragment<FragmentFirstBinding, SecondFragmentViewModel> {

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_second);
    }

    @NonNull
    @Override
    public SecondFragmentViewModel createViewModel() {
        return new SecondFragmentViewModel();
    }
}
