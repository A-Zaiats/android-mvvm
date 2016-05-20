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

package io.github.azaiats.androidmvvm.core.common;

import android.support.annotation.Nullable;

/**
 * ViewModel with navigation
 * <p>
 * ViewModel has no direct reference to context. So it can't starts new {@link android.app.Activity}
 * or replace {@link android.app.Fragment} via {@link android.app.FragmentManager}.
 * This work can be delegated to external navigator.
 *
 * @param <T> the type of {@link Navigator}
 * @author Andrei Zaiats
 * @since 0.1.1
 */
public abstract class NavigatingViewModel<T extends Navigator> extends BaseViewModel {

    @Nullable protected T navigator;

    /**
     * Setter for navigator
     * @param navigator {@link Navigator} for this ViewModel
     */
    public void setNavigator(@Nullable T navigator) {
        this.navigator = navigator;
    }
}
