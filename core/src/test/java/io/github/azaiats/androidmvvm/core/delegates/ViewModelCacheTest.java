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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.github.azaiats.androidmvvm.core.BuildConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.testutils.ReflectionUtils;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

/**
 * @author Andrei Zaiats
 * @since 0.2.0
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class ViewModelCacheTest {

    private ViewModelCache cache;
    private AppCompatActivity testActivity;

    @Before
    public void init() {
        cache = new ViewModelCache();
        testActivity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
    }

    @Test
    public void testKeysUnique() {
        final int firstKey = cache.getSpareKey(testActivity);
        final int secondKey = cache.getSpareKey(testActivity);
        final int thirdKey = cache.getSpareKey(testActivity);

        assertNotEquals(firstKey, secondKey);
        assertNotEquals(firstKey, thirdKey);
        assertNotEquals(secondKey, thirdKey);
    }

    @Test
    public void testChecksExistingKeys() {
        final int key = cache.getSpareKey(testActivity);
        final int nextKey = key + 1;
        cache.putViewModel(key, mock(MvvmViewModel.class), testActivity);
        cache.putViewModel(nextKey, mock(MvvmViewModel.class), testActivity);

        assertNotEquals(nextKey, cache.getSpareKey(testActivity));
    }

    @Test
    public void testGetPutted() {
        final int key = 1;
        final MvvmViewModel viewModel = mock(MvvmViewModel.class);
        cache.putViewModel(key, viewModel, testActivity);

        assertSame(viewModel, cache.getViewModel(key, testActivity));
    }

    @Test
    public void testDelete() {
        final int key = 1;
        final MvvmViewModel viewModel = mock(MvvmViewModel.class);
        cache.putViewModel(key, viewModel, testActivity);
        cache.removeViewModel(key, testActivity);

        assertNull(cache.getViewModel(key, testActivity));
    }

    @Test
    public void testRemoveCacheFragmentOnCleanup() {
        cache.getViewModel(1, testActivity);
        assertNotNull(getCacheFragment(cache));
        cache.cleanUp();
        assertNull(getCacheFragment(cache));
    }

    @Test
    public void testReuseRetainedFragment() {
        cache.getViewModel(1, testActivity);
        final Fragment oldFragment = getCacheFragment(cache);

        cache.cleanUp();
        cache = new ViewModelCache();
        cache.getViewModel(1, testActivity);
        final Fragment newFragment = getCacheFragment(cache);

        assertSame(oldFragment, newFragment);
    }

    @Test(expected = IllegalStateException.class)
    public void testFailIfContextNotContainsFragmentActivity() {
        Activity activity = Robolectric.buildActivity(Activity.class).create().get();
        cache.getViewModel(1, activity);
    }

    private Fragment getCacheFragment(ViewModelCache cache) {
        return (Fragment) ReflectionUtils.getPrivateField(cache, "cacheFragment");
    }
}
