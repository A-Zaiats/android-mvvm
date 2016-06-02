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

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.core.common.Navigator;
import io.github.azaiats.androidmvvm.core.delegates.FragmentDelegate;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingFragmentDelegate;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrei Zaiats
 */
public class NavigatingMvvmDialogFragmentTest {

    private NavigatingMvvmDialogFragment fragment;

    @Before
    public void init() {
        fragment = new NavigatingMvvmDialogFragment() {
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

            @NonNull
            @Override
            public Navigator getNavigator() {
                return null;
            }
        };
    }

    @Test
    public void testFragmentReturnsNavigatingDelegate() {
        assertTrue(fragment.getMvvmDelegate() instanceof NavigatingFragmentDelegate);
    }

    @Test
    public void testCreateDelegateIfNotExestsOnly() {
        final FragmentDelegate firstDelegate = fragment.getMvvmDelegate();
        final FragmentDelegate secondDelegate = fragment.getMvvmDelegate();
        assertSame(firstDelegate, secondDelegate);
    }
}
