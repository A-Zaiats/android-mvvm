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

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.core.mocks.TestMvvmDialogFragment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

/**
 * @author Andrei Zaiats
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class MvvmDialogFragmentTest {

    private TestMvvmDialogFragment fragment;

    @Before
    public void init() {
        fragment = new TestMvvmDialogFragment();
    }

    @Test
    public void testStartMethodsDelegated() {
        SupportFragmentTestUtil.startFragment(fragment);
        verify(fragment.getMvvmDelegate()).onCreate(null);
        verify(fragment.getMvvmDelegate()).onResume();
    }

    @Test
    public void testOnPauseDelegated() {
        SupportFragmentTestUtil.startFragment(fragment);
        fragment.onPause();
        verify(fragment.getMvvmDelegate()).onPause();
    }

    @Test
    public void testOnDestroyDelegated() {
        SupportFragmentTestUtil.startFragment(fragment);
        fragment.onDestroy();
        verify(fragment.getMvvmDelegate()).onDestroy();
    }

    @Test
    public void testOnSaveInstanceStateDelegated() {
        SupportFragmentTestUtil.startFragment(fragment);
        final Bundle testBundle = new Bundle();
        fragment.onSaveInstanceState(testBundle);
        verify(fragment.getMvvmDelegate()).onSaveInstanceState(testBundle);
    }

    @Test(expected = IllegalStateException.class)
    public void testNotAllowUseBindingBeforeCreation() {
        getTestedFragment().getBinding();
    }

    @Test(expected = IllegalStateException.class)
    public void testNotAllowUseViewModelBeforeCreation() {
        getTestedFragment().getViewModel();
    }

    @Test
    public void testCreateDelegateIfNotExists() {
        assertNotNull(getTestedFragment().getMvvmDelegate());
    }

    @Test
    public void testReturnSameDelegate() {
        final MvvmDialogFragment testedFragment = getTestedFragment();
        assertSame(testedFragment.getMvvmDelegate(), testedFragment.getMvvmDelegate());
    }

    private MvvmDialogFragment getTestedFragment() {
        return new MvvmDialogFragment() {
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
