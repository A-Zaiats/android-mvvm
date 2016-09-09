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

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.SparseArrayCompat;

import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * A internal class responsible to save ViewModel instances during screen orientation changes.
 *
 * @author Andrei Zaiats
 * @since 0.2.0
 */
class ViewModelCache {

    private static final String FRAGMENT_TAG = "io.github.azaiats.androidmvvm.core.delegates.ViewModelCacheFragment";
    private ViewModelCacheFragment cacheFragment = null;

    /**
     * Get a spare key for cache
     *
     * @param context the context
     * @return a spare key
     */
    int getSpareKey(@NonNull Context context) {
        return getFragment(context).getSpareKey();
    }

    /**
     * Get a ViewModel from cache
     *
     * @param key the key
     * @param context the context
     * @return the cached ViewModel or <code>null</code> if no one found for the given key.
     */
    public MvvmViewModel getViewModel(int key, @NonNull Context context) {
        return getFragment(context).get(key);
    }

    /**
     * Put a ViewModel into the cache
     *
     * @param key the key
     * @param cacheViewModel the cache ViewModel
     * @param context the context
     */
    public void putViewModel(int key, MvvmViewModel cacheViewModel, Context context) {
        getFragment(context).put(key, cacheViewModel);
    }

    /**
     * Remove a ViewModel from the specified key
     *
     * @param key the key
     * @param context the context
     */
    public void removeViewModel(int key, Context context) {
        getFragment(context).remove(key);
    }

    /**
     * Call on destroy to avoid memory leaks
     */
    public void cleanUp() {
        cacheFragment = null;
    }

    private ViewModelCacheFragment getFragment(Context context) {
        if (cacheFragment != null) return cacheFragment;

        final FragmentActivity activity = getActivity(context);

        final ViewModelCacheFragment fragment = (ViewModelCacheFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(FRAGMENT_TAG);

        // Existing Fragment found
        if (fragment != null) {
            cacheFragment = fragment;
            return fragment;
        }

        // No Fragment found, so create a new one
        cacheFragment = new ViewModelCacheFragment();
        activity.getSupportFragmentManager().beginTransaction().add(cacheFragment, FRAGMENT_TAG).commit();
        return cacheFragment;
    }

    private FragmentActivity getActivity(Context context) {
        Context wrapper = context;
        while (wrapper instanceof ContextWrapper) {
            if (wrapper instanceof FragmentActivity) return (FragmentActivity) wrapper;
            wrapper = ((ContextWrapper) wrapper).getBaseContext();
        }
        throw new IllegalStateException("Could not find the surrounding FragmentActivity. Does your activity extends "
                + "from android.support.v4.app.FragmentActivity like android.support.v7.app.AppCompatActivity ?");
    }

    /**
     * Fragment used to cache ViewModel on screen orientation changes
     *
     * @author Andrei Zaiats
     * @since 0.2.0
     */
    public static final class ViewModelCacheFragment extends Fragment {
        private SparseArrayCompat<MvvmViewModel> cache = new SparseArrayCompat<>();
        private int currentKey = 0;

        /**
         * Get a ViewModel from cache
         *
         * @param key the key
         * @return the cached ViewModel or <code>null</code> if no one found for the given key.
         */
        @Nullable
        MvvmViewModel get(int key) {
            return cache.get(key);
        }

        /**
         * Put a ViewModel into the cache
         *
         * @param key the key
         * @param cacheViewModel the cache ViewModel
         */
        void put(int key, MvvmViewModel cacheViewModel) {
            cache.put(key, cacheViewModel);
        }

        /**
         * Remove a ViewModel from the specified key
         *
         * @param key the key
         */
        void remove(int key) {
            if (cache != null) {
                cache.remove(key);
            }
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onDestroy() {
            cache.clear();
            cache = null;
            super.onDestroy();
        }

        /**
         * Get a spare key for cache
         *
         * @return a spare key
         */
        int getSpareKey() {
            while (cache.get(++currentKey) != null) { }
            return currentKey;
        }
    }
}
