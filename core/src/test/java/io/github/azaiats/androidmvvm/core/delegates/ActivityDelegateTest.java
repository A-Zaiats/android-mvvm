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

import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Zaiats
 */
public class ActivityDelegateTest {

    @Mock
    private ActivityDelegateCallback callback;

    @Mock
    private Activity activity;

    @Mock
    private MvvmViewModel viewModel;

    @Mock
    private MvvmView view;

    @InjectMocks
    private ActivityDelegate activityDelegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDestroyViewModelWhenInBackStack() {
        activityDelegate.viewModel = viewModel;
        when(activity.isChangingConfigurations()).thenReturn(false);
        activityDelegate.onRetainCustomNonConfigurationInstance();
        verify(viewModel).onDestroy();
    }

    @Test
    public void testNotDestroyViewModelOnConfigurationChange() {
        activityDelegate.viewModel = viewModel;
        when(activity.isChangingConfigurations()).thenReturn(true);
        activityDelegate.onRetainCustomNonConfigurationInstance();
        verify(viewModel, never()).onDestroy();
    }

    @Test
    public void testNoNullPointerOnDestroyViewModelIfAlreadyRemoved() {
        when(activity.isChangingConfigurations()).thenReturn(true);
        assertNull(activityDelegate.viewModel);
        activityDelegate.onDestroy();
    }

    @Test
    public void testNotCacheViewModelWhenInBackStack() {
        activityDelegate.viewModel = viewModel;
        when(activity.isChangingConfigurations()).thenReturn(false);
        assertNull(activityDelegate.onRetainCustomNonConfigurationInstance());
    }

    @Test
    public void testCacheViewModelOnConfigurationChange() {
        activityDelegate.viewModel = viewModel;
        when(activity.isChangingConfigurations()).thenReturn(true);
        activityDelegate.onRetainCustomNonConfigurationInstance();
        assertSame(viewModel, activityDelegate.onRetainCustomNonConfigurationInstance());
    }

    @Test
    public void testDelegateToActivityFinishingCheck() {
        when(activity.isChangingConfigurations()).thenReturn(false).thenReturn(true);
        assertTrue(activityDelegate.isFinished());
        assertFalse(activityDelegate.isFinished());
    }

    @Test
    public void testGetCachedViewModelFromCallback() {
        when(callback.getLastCustomNonConfigurationInstance()).thenReturn(viewModel);
        assertSame(activityDelegate.getCachedViewModel(), viewModel);
    }

}
