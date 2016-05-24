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

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * The MvvmDelegate callback for activity.
 * <p>
 * This callback will be called from {@link ActivityDelegate}.
 * It must be implemented by all Activities that you want to support library's mvvm.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @see io.github.azaiats.androidmvvm.core.MvvmActivity for example of implementation.
 * @since 0.1.0
 */
public interface ActivityDelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel>
        extends DelegateCallback<T, S> {

    /**
     * This method should invoke {@link android.support.v4.app.FragmentActivity#getLastCustomNonConfigurationInstance()}
     *
     * @return the value previously returned from
     * {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()}
     */
    @Nullable
    Object getLastCustomNonConfigurationInstance();
}
