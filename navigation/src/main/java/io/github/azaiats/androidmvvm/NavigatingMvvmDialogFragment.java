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

package io.github.azaiats.androidmvvm;

import android.databinding.ViewDataBinding;

import io.github.azaiats.androidmvvm.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.common.Navigator;
import io.github.azaiats.androidmvvm.delegates.DelegateCallback;
import io.github.azaiats.androidmvvm.delegates.MvvmDelegate;
import io.github.azaiats.androidmvvm.delegates.NavigatingDelegateCallback;
import io.github.azaiats.androidmvvm.delegates.NavigatingFragmentDelegate;

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
        extends MvvmDialogFragment<S, U> implements DelegateCallback<S, U>, NavigatingDelegateCallback<T> {

    private NavigatingFragmentDelegate<T, S, U> delegate;

    @Override
    protected MvvmDelegate<S, U> getMvvmDelegate() {
        if (delegate == null) {
            delegate = new NavigatingFragmentDelegate<>(this, this, getActivity());
        }
        return delegate;
    }
}
