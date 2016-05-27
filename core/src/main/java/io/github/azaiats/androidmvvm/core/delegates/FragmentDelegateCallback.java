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

import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * The MvvmDelegate callback for fragment.
 * <p>
 * This callback will be called from {@link FragmentDelegate}.
 * It must be implemented by all Fragments that you want to support library's mvvm.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.2.0
 */
public interface FragmentDelegateCallback<T extends ViewDataBinding, S extends MvvmViewModel>
        extends DelegateCallback<T, S> {
}
