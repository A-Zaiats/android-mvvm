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

package io.github.azaiats.androidmvvm.core;

import android.databinding.Observable;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.core.mocks.TestMvvmActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Andrei Zaiats
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class MvvmActivityTest {

    private MvvmActivity activity;
    private ActivityController<TestMvvmActivity> activityController;

    @Before
    public void init() {
        activityController = Robolectric.buildActivity(TestMvvmActivity.class);
        activity = activityController.get();
    }

    @Test
    public void testOnCreateDelegated() {
        activityController.create();
        verify(activity.getMvvmDelegate()).onCreate();
    }

    @Test
    public void testOnDestroyDelegated() {
        activityController.create().destroy();
        verify(activity.getMvvmDelegate()).onDestroy();
    }

    @Test
    public void testOnPauseDelegated() {
        activityController.create().pause();
        verify(activity.getMvvmDelegate()).onPause();
    }

    @Test
    public void testOnResumeDelegated() {
        activityController.create();
        activity.onResume();
        verify(activity.getMvvmDelegate()).onResume();
    }

    @Test
    public void testOnRetainCustomNonConfigurationInstanceDelegated() {
        activity.onRetainNonConfigurationInstance();
        verify(activity.getMvvmDelegate()).onRetainCustomNonConfigurationInstance();
    }

    @Test(expected = IllegalStateException.class)
    public void testAvoidBindingUsageBeforeInitialization() {
        activity.getBinding();
    }

    @Test(expected = IllegalStateException.class)
    public void testAvoidViewModelUsageBeforeInitialization() {
        activity.getViewModel();
    }

    @Test
    public void testOnPropertyChangedCallbacksAddedToViewModel() {
        final Observable.OnPropertyChangedCallback firstCallback = mock(Observable.OnPropertyChangedCallback.class);
        final Observable.OnPropertyChangedCallback secondCallback = mock(Observable.OnPropertyChangedCallback.class);
        activityController.create();
        activity.setViewModel(activity.createViewModel());

        activity.addOnPropertyChangedCallback(firstCallback);
        activity.addOnPropertyChangedCallback(secondCallback);

        verify(activity.getViewModel()).addOnPropertyChangedCallback(firstCallback);
        verify(activity.getViewModel()).addOnPropertyChangedCallback(secondCallback);
    }

    @Test
    public void testRemoveOnPropertyChangedCallbacksOnActivityDestroy() {
        final Observable.OnPropertyChangedCallback firstCallback = mock(Observable.OnPropertyChangedCallback.class);
        final Observable.OnPropertyChangedCallback secondCallback = mock(Observable.OnPropertyChangedCallback.class);
        activityController.create();
        activity.setViewModel(activity.createViewModel());
        activity.addOnPropertyChangedCallback(firstCallback);
        activity.addOnPropertyChangedCallback(secondCallback);

        activity.onDestroy();
        verify(activity.getViewModel()).removeOnPropertyChangedCallback(firstCallback);
        verify(activity.getViewModel()).removeOnPropertyChangedCallback(secondCallback);
    }

    @Test
    public void testCreateDelegateIfNotExists() {
        assertNotNull(getTestedActivity().getMvvmDelegate());
    }

    @Test
    public void testReturnsSameDelegate() {
        final MvvmActivity testedActivity = getTestedActivity();
        assertSame(testedActivity.getMvvmDelegate(), testedActivity.getMvvmDelegate());
    }

    private MvvmActivity getTestedActivity() {
        return new MvvmActivity() {
            @NonNull
            @Override
            public MvvmViewModel createViewModel() {
                return null;
            }

            @NonNull
            @Override
            public BindingConfig getBindingConfig() {
                return null;
            }
        };
    }
}
