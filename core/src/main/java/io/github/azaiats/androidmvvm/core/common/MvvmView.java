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

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

/**
 * The root view interface for every mvvm view.
 *
 * @param <T> generated Data Binding layout class
 * @param <S> ViewModel binded to this view
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public interface MvvmView<T extends ViewDataBinding, S extends MvvmViewModel> {

    /**
     * Create binging config for this view.
     *
     * @return view binding config.
     */
    @NonNull
    BindingConfig getBindingConfig();

    /**
     * Setter for Data Binding instance.
     *
     * @param binding the binding for this view
     */
    void setBinding(@NonNull T binding);

    /**
     * Setter for ViewModel instance.
     *
     * @param viewModel binded ViewModel
     */
    void setViewModel(@NonNull S viewModel);
}
