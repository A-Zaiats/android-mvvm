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

import android.databinding.ViewDataBinding;

import io.github.azaiats.androidmvvm.core.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.core.common.Navigator;
import io.github.azaiats.androidmvvm.core.delegates.FragmentDelegate;
import io.github.azaiats.androidmvvm.core.delegates.FragmentDelegateCallback;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingDelegateCallback;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingFragmentDelegate;

/**
 * MvvmDialogFragment that supports Navigator
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 * @author Andrei Zaiats
 * @since 0.2.0
 */
public abstract class NavigatingMvvmDialogFragment<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>>
        extends MvvmDialogFragment<S, U> implements FragmentDelegateCallback<S, U>, NavigatingDelegateCallback<T> {

    private NavigatingFragmentDelegate<T, S, U> delegate;

    @Override
    protected FragmentDelegate getMvvmDelegate() {
        if (delegate == null) {
            delegate = new NavigatingFragmentDelegate<>(this, this, this);
        }
        return delegate;
    }
}
