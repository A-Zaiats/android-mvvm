package io.github.azaiats.androidmvvm.core.mocks;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.core.MvvmActivity;
import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.core.delegates.ActivityDelegate;

import static org.mockito.Mockito.mock;

/**
 * @author Andrei Zaiats
 */
public class TestMvvmActivity extends MvvmActivity {

    private MvvmViewModel viewModel = mock(MvvmViewModel.class);
    private BindingConfig bindingConfig = mock(BindingConfig.class);
    private ActivityDelegate delegate = mock(ActivityDelegate.class);

    @NonNull
    @Override
    public MvvmViewModel createViewModel() {
        return viewModel;
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return bindingConfig;
    }

    @NonNull
    @Override
    protected ActivityDelegate getMvvmDelegate() {
        return delegate;
    }
}
