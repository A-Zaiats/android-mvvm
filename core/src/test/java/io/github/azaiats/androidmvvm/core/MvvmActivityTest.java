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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.github.azaiats.androidmvvm.core.delegates.ActivityDelegate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Zaiats
 */
public class MvvmActivityTest {

    private static final String IGNORE_REASON = "need Robolectric integration";
    @Mock
    private ActivityDelegate delegate;

    @Spy
    private MvvmActivity activity;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(activity.getMvvmDelegate()).thenReturn(delegate);
    }

    @Ignore(IGNORE_REASON)
    @Test
    public void testOnCreateDelegated() {
        activity.onCreate(null);
        verify(delegate).onCreate();
    }

    @Ignore(IGNORE_REASON)
    @Test
    public void testOnDestroyDelegated() {
        activity.onDestroy();
        verify(delegate).onDestroy();
    }

    @Ignore(IGNORE_REASON)
    @Test
    public void testOnPauseDelegated() {
        activity.onPause();
        verify(delegate).onPause();
    }

    @Ignore(IGNORE_REASON)
    @Test
    public void testOnResumeDelegated() {
        activity.onResume();
        verify(delegate).onResume();
    }

    @Test
    public void testOnRetainCustomNonConfigurationInstanceDelegated() {
        activity.onRetainNonConfigurationInstance();
        verify(delegate).onRetainCustomNonConfigurationInstance();
    }
}
