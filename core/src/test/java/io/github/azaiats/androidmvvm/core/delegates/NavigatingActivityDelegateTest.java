/*
 * Copyright 2016 Andrei Zaiats.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.azaiats.androidmvvm.core.delegates;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.core.common.Navigator;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Zaiats
 */
public class NavigatingActivityDelegateTest {

    @Mock
    private Navigator navigator;

    @Mock
    private NavigatingActivityDelegateCallback callback;

    @Mock
    private NavigatingViewModel<Navigator> viewModel;

    @Mock
    private Activity delegatedActivity;

    @Mock
    private MvvmView view;

    @InjectMocks
    private TestNavigatingActivityDelegate delegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(callback.getNavigator()).thenReturn(navigator);
    }

    @Test
    public void testSetNavigatorOnCreate() {
        when(callback.getView()).thenReturn(view);
        when(callback.createViewModel()).thenReturn(viewModel);
        when(view.getBindingConfig()).thenReturn(new BindingConfig(0));
        delegate.onCreate();
        verify(viewModel).setNavigator(navigator);
    }

    @Test
    public void testRemoveNavigatorOnDestroy() {
        delegate.viewModel = viewModel;
        delegate.onDestroy();
        verify(viewModel).setNavigator(null);
    }

    @Test
    public void testNoNullPointerOnNavigatorDeleteIfViewModelRemoved() {
        assertNull(delegate.viewModel);
        delegate.onDestroy();
    }
}
