package io.github.azaiats.androidmvvm.delegates;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.azaiats.androidmvvm.common.BindingConfig;
import io.github.azaiats.androidmvvm.common.MvvmView;
import io.github.azaiats.androidmvvm.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.testutils.ReflectionUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Zaiats
 */
public class MvvmDelegateTest {

    @Mock
    private DelegateCallback callback;

    @Mock
    private MvvmViewModel viewModel;

    @Mock
    private MvvmView view;

    @Mock
    private Bundle bundle;

    @Mock
    private ViewModelCache cache;

    @InjectMocks
    private MvvmDelegate delegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(callback.getMvvmView()).thenReturn(view);
        when(view.getBindingConfig()).thenReturn(new BindingConfig(0));
    }

    @Test
    public void testDestroyViewModelIfActivityFinished() {
        delegate.viewModel = viewModel;
        delegate.onDestroy();
        verify(viewModel).onDestroy();
    }

    @Test
    public void testCreateViewModelIfNotCached() {
        ReflectionUtils.setPrivateField(delegate, "cache", cache);
        when(callback.createViewModel()).thenReturn(viewModel);
        Assert.assertSame(viewModel, delegate.initViewModel());
        verify(viewModel).onCreate();
    }
}
