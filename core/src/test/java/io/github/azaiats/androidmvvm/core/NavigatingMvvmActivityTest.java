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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import io.github.azaiats.androidmvvm.core.delegates.ActivityDelegate;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingActivityDelegate;
import io.github.azaiats.androidmvvm.core.mocks.TestNavigatingMvvmActivity;

import static org.junit.Assert.assertTrue;

/**
 * @author Andrei Zaiats
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class NavigatingMvvmActivityTest {

    @Test
    public void testActivityReturnsNavigatingDelegate() {
        final NavigatingMvvmActivity activity = Robolectric.buildActivity(TestNavigatingMvvmActivity.class).get();
        final ActivityDelegate mvvmDelegate = activity.getMvvmDelegate();
        assertTrue(mvvmDelegate instanceof NavigatingActivityDelegate);
    }
}
