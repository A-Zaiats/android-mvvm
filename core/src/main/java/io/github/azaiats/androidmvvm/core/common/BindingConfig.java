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

import android.support.annotation.LayoutRes;

import io.github.azaiats.androidmvvm.core.BR;

/**
 * Binging configuration params holder.
 * <p>
 * Use it to define a binding configuration params for a specific view.
 *
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public class BindingConfig {

    @LayoutRes
    private final int layoutResource;
    private final int viewModelVariableName;

    /**
     * Create a BindingConfig object for an Activity/Fragment.
     *
     * @param layoutResource        layout resource id
     * @param viewModelVariableName data binding variable name for attached ViewModel -
     *                              must be generated id (e.g. BR.varName)
     */
    public BindingConfig(@LayoutRes int layoutResource, int viewModelVariableName) {
        this.layoutResource = layoutResource;
        this.viewModelVariableName = viewModelVariableName;
    }

    /**
     * Create a BindingConfig object for an Activity/Fragment with default variable name.
     *
     * @param layoutResource layout resource id
     */
    public BindingConfig(@LayoutRes int layoutResource) {
        this(layoutResource, BR.viewModel);
    }

    /**
     * Returns layout resource id.
     *
     * @return layout resource id.
     */
    public int getLayoutResource() {
        return layoutResource;
    }

    /**
     * Returns variable name for attached ViewModel.
     *
     * @return ViewModel variable name.
     */
    public int getViewModelVariableName() {
        return viewModelVariableName;
    }
}
