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
import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.core.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.core.common.Navigator;

/**
 * The callback for navigated activity
 *
 * @param <T> the type of {@link Navigator}
 * @param <S> the type of {@link ViewDataBinding}
 * @param <U> the type of binded {@link NavigatingViewModel}
 * @author Andrei Zaiats
 * @since 0.1.1
 */
public interface NavigatingActivityDelegateCallback<T extends Navigator, S extends ViewDataBinding,
        U extends NavigatingViewModel<T>>
        extends ActivityDelegateCallback<S, U> {

    /**
     * Getter for Navigator
     *
     * @return concrete Navigator instance
     */
    @NonNull T getNavigator();
}
