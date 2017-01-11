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

package io.github.azaiats.androidmvvm.delegates;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.common.MvvmView;
import io.github.azaiats.androidmvvm.common.MvvmViewModel;

/**
 * The base callback for Activity/Fragment delegates.
 * <p>
 * The base delegate callback functionality. Don't use it by your own.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public interface DelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel> {

    /**
     * Getter for processed view.
     *
     * @return the processed {@link MvvmView}
     */
    @NonNull
    MvvmView<T, S> getMvvmView();

    /**
     * Create a ViewModel instance.
     *
     * @return the {@link MvvmViewModel} for the view
     */
    @NonNull
    S createViewModel();
}
