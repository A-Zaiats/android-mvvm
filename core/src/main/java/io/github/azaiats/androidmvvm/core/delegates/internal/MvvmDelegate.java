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

package io.github.azaiats.androidmvvm.core.delegates.internal;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * A base delegate for Activities/Fragments lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate()}
 * <li>{@link #onResume()}
 * <li>{@link #onPause()}
 * <li>{@link #onDestroy()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public abstract class MvvmDelegate<T extends ViewDataBinding, S extends MvvmViewModel> {

    private static final String BINDING_VARIABLE_ERROR_TEXT = "Binding variable wasn't set successfully. "
            + "Probably viewModelVariableName of your BindingConfig doesn't match any variable in %s.";

    @Nullable
    protected S viewModel;

    private final DelegateCallback<T, S> callback;

    /**
     * Create a delegate.
     *
     * @param callback the {@link DelegateCallback} for this delegate
     */
    public MvvmDelegate(DelegateCallback<T, S> callback) {
        this.callback = callback;
    }

    /**
     * This method must be called from {@link android.app.Activity#onCreate(android.os.Bundle)}
     * <p>
     * Create a ViewDataBinding and a MvvmViewModel, attach them to the processed view.
     */
    @CallSuper
    public void onCreate() {
        final MvvmView<T, S> view = callback.getView();
        final BindingConfig bindingConfig = view.getBindingConfig();
        final S viewModel = initViewModel();
        final T binding = initBinding(bindingConfig);
        view.setBinding(binding);
        view.setViewModel(viewModel);
    }

    /**
     * This method must be called from {@link android.app.Activity#onResume()}
     * or {@link android.support.v4.app.Fragment#onResume()}.
     */
    @CallSuper
    public void onResume() {
        if (viewModel != null) viewModel.onResume();
    }

    /**
     * This method must be called from {@link android.app.Activity#onPause()}
     * or {@link android.support.v4.app.Fragment#onPause()}.
     */
    @CallSuper
    public void onPause() {
        if (viewModel != null) viewModel.onPause();
    }

    /**
     * This method must be called from {@link android.app.Activity#onDestroy()}
     * or {@link android.support.v4.app.Fragment#onDestroy()}.
     */
    @CallSuper
    public void onDestroy() {
        if (viewModel == null) return;

        if (isFinished()) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

    protected abstract boolean isFinished();

    @Nullable
    protected abstract S getCachedViewModel();

    @NonNull
    protected abstract T createDataBinding(@LayoutRes int layoutResource);

    protected S initViewModel() {
        S viewModel = getCachedViewModel();
        if (viewModel == null) {
            viewModel = callback.createViewModel();
            viewModel.onCreate();
        }
        this.viewModel = viewModel;
        return viewModel;
    }

    protected T initBinding(BindingConfig bindingConfig) {
        T binding = createDataBinding(bindingConfig.getLayoutResource());
        if (!binding.setVariable(bindingConfig.getViewModelVariableName(), this.viewModel)) {
            throw new IllegalArgumentException(
                    String.format(BINDING_VARIABLE_ERROR_TEXT,
                            binding.getClass().getSimpleName()
                    ));
        }
        return binding;
    }
}
