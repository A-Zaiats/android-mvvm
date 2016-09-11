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

package io.github.azaiats.androidmvvm.navigation.delegates;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.github.azaiats.androidmvvm.core.delegates.DelegateCallback;
import io.github.azaiats.androidmvvm.core.delegates.FragmentDelegate;
import io.github.azaiats.androidmvvm.navigation.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.navigation.common.Navigator;

/**
 * A navigated delegate for Fragments lifecycle.
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 * @author Andrei Zaiats
 * @since 0.2.0
 */
public class NavigatingFragmentDelegate<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>> extends FragmentDelegate<S, U> {

    private final NavigatingDelegateCallback<T> navigatingCallback;

    /**
     * Create a navigated delegate for fragment.
     *
     * @param callback          the {@link DelegateCallback} for this delegate
     * @param navigatingCallback the {@link NavigatingDelegateCallback} for this delegate
     * @param delegatedFragment {@link Fragment} for delegation
     */
    public NavigatingFragmentDelegate(@NonNull DelegateCallback<S, U> callback,
                                      @NonNull NavigatingDelegateCallback<T> navigatingCallback,
                                      @NonNull Fragment delegatedFragment) {
        super(callback, delegatedFragment);
        this.navigatingCallback = navigatingCallback;
    }

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        if (viewModel != null) {
            viewModel.setNavigator(navigatingCallback.getNavigator());
        }
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.setNavigator(null);
        }
        super.onDestroy();
    }
}
