package io.github.azaiats.androidmvvm.core.delegates.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.core.utils.ReflectionUtils;

import static org.mockito.Mockito.never;
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

    //@Mock
    //private BindingConfig bindingConfig;

    @Mock
    private MvvmDelegate delegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        delegate.viewModel = viewModel;
        Mockito.doCallRealMethod().when(delegate).onCreate();
        Mockito.doCallRealMethod().when(delegate).onResume();
        Mockito.doCallRealMethod().when(delegate).onPause();
        Mockito.doCallRealMethod().when(delegate).onDestroy();
        Mockito.doCallRealMethod().when(delegate).initViewModel();

        when(callback.getView()).thenReturn(view);
        when(view.getBindingConfig()).thenReturn(new BindingConfig(0));
    }

    @Test
    public void testResumeViewModelOnActivityResume() {
        delegate.onResume();
        verify(viewModel).onResume();
    }

    @Test
    public void testPauseViewModelOnActivityPause() {
        delegate.onPause();
        verify(viewModel).onPause();
    }

    @Test
    public void testDestroyViewModelIfActivityFinished() {
        when(delegate.isFinished()).thenReturn(true);
        delegate.onDestroy();
        verify(viewModel).onDestroy();
    }

    @Test
    public void testNotDestroyViewModelIfActivityNotFinished() {
        when(delegate.isFinished()).thenReturn(false);
        delegate.onDestroy();
        verify(viewModel, never()).onDestroy();
    }

    @Test
    public void testCreateViewModelIfNotCached() {
        setCallback();
        when(delegate.getCachedViewModel()).thenReturn(null);
        when(callback.createViewModel()).thenReturn(viewModel);
        Assert.assertSame(viewModel, delegate.initViewModel());
        verify(viewModel).onCreate();
    }

    @Test
    public void testUseCachedViewModelIfExists() {
        setCallback();
        when(delegate.getCachedViewModel()).thenReturn(viewModel);
        Assert.assertSame(viewModel, delegate.initViewModel());
        verify(viewModel, never()).onCreate();
    }

    @Test
    public void testViewModelAttachedToView() {
        setCallback();
        when(delegate.getCachedViewModel()).thenReturn(viewModel);
        delegate.onCreate();
        verify(view).setViewModel(viewModel);
    }

    private void setCallback() {
        ReflectionUtils.setParentField(delegate, "callback", callback);
    }
}
