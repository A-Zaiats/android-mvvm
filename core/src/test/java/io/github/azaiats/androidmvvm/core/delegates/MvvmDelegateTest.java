package io.github.azaiats.androidmvvm.core.delegates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

import static org.junit.Assert.assertNull;
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

    @InjectMocks
    private TestMvvmDelegate delegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(callback.getView()).thenReturn(view);
        when(view.getBindingConfig()).thenReturn(new BindingConfig(0));
    }

    @Test
    public void testResumeViewModelOnActivityResume() {
        delegate.viewModel = viewModel;
        delegate.onResume();
        verify(viewModel).onResume();
    }

    @Test
    public void testPauseViewModelOnActivityPause() {
        delegate.viewModel = viewModel;
        delegate.onPause();
        verify(viewModel).onPause();
    }

    @Test
    public void testDestroyViewModelIfActivityFinished() {
        delegate.setFinished(true);
        delegate.viewModel = viewModel;
        delegate.onDestroy();
        verify(viewModel).onDestroy();
    }

    @Test
    public void testNotDestroyViewModelIfActivityNotFinished() {
        delegate.setFinished(false);
        delegate.viewModel = viewModel;
        delegate.onDestroy();
        verify(viewModel, never()).onDestroy();
    }

    @Test
    public void testCreateViewModelIfNotCached() {
        when(callback.createViewModel()).thenReturn(viewModel);
        Assert.assertSame(viewModel, delegate.initViewModel());
        verify(viewModel).onCreate();
    }

    @Test
    public void testUseCachedViewModelIfExists() {
        delegate.setCachedViewModel(viewModel);
        Assert.assertSame(viewModel, delegate.initViewModel());
        verify(viewModel, never()).onCreate();
    }

    @Test
    public void testViewModelAttachedToView() {
        delegate.setCachedViewModel(viewModel);
        delegate.onCreate();
        verify(view).setViewModel(viewModel);
    }

    @Test
    public void testNoNullPointerIfViewModelNull() {
        assertNull(delegate.viewModel);
        delegate.onResume();
        assertNull(delegate.viewModel);
        delegate.onPause();
        assertNull(delegate.viewModel);
        delegate.onDestroy();
    }
}
