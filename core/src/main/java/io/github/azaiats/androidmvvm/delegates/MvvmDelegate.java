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

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import io.github.azaiats.androidmvvm.common.BindingConfig;
import io.github.azaiats.androidmvvm.common.MvvmView;
import io.github.azaiats.androidmvvm.common.MvvmViewModel;

/**
 * A delegate for Activities/Fragments lifecycle.
 * <p>
 * The following methods must be proxied from the lifecycle methods:
 * <ul>
 * <li>{@link #onCreate(Bundle)}
 * <li>{@link #onSaveInstanceState(Bundle)}
 * <li>{@link #onDestroy()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public class MvvmDelegate<T extends ViewDataBinding, S extends MvvmViewModel> {

    private static final String BINDING_VARIABLE_ERROR_TEXT = "Binding variable wasn't set successfully. "
            + "Probably viewModelVariableName of your BindingConfig doesn't match any variable in %s.";

    private static final String VIEW_MODEL_KEY_NAME
            = "MvvmDelegate.VIEW_MODEL_KEY_NAME";

    @Nullable
    protected S viewModel;

    private final DelegateCallback<T, S> callback;
    private final Context context;
    private ViewModelCache cache;
    private int viewModelKey = 0;

    /**
     * Create a delegate.
     *
     * @param callback the {@link DelegateCallback} for this delegate
     * @param context an android context
     */
    public MvvmDelegate(@NonNull DelegateCallback<T, S> callback, @NonNull Context context) {
        this.callback = callback;
        this.context = context;
        cache = new ViewModelCache();
    }

    /**
     * This method must be called from {@link android.app.Activity#onCreate(android.os.Bundle)}
     * <p>
     * Create a ViewDataBinding and a MvvmViewModel, attach them to the processed view.
     *
     * @param savedInstanceState bundle with a saved state
     */
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            viewModelKey = savedInstanceState.getInt(VIEW_MODEL_KEY_NAME, 0);
        }
        final MvvmView<T, S> view = callback.getMvvmView();
        final BindingConfig bindingConfig = view.getBindingConfig();
        final S viewModel = initViewModel();
        final T binding = initBinding(bindingConfig);
        view.setBinding(binding);
        view.setViewModel(viewModel);
    }

    /**
     * Save current ViewModel to cache
     *
     * @param bundle Bundle in which to place key for cached ViewModel.
     */
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        final int key = viewModelKey == 0 ? cache.getSpareKey(context) : viewModelKey;
        cache.putViewModel(key, viewModel, context);

        bundle.putInt(VIEW_MODEL_KEY_NAME, key);
    }

    /**
     * This method must be called from {@link android.app.Activity#onDestroy()}
     * or {@link android.support.v4.app.Fragment#onDestroy()}.
     */
    @CallSuper
    public void onDestroy() {
        if (viewModel == null) return;

        removeViewModel();
        cache.cleanUp();
        viewModel.onDestroy();
        viewModel = null;
    }

    @NonNull
    protected T createDataBinding(@LayoutRes int layoutResource) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        return DataBindingUtil.inflate(layoutInflater, layoutResource, null, false);
    }

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

    private S getCachedViewModel() {
        return (S) cache.getViewModel(viewModelKey, context);
    }

    private void removeViewModel() {
        if (viewModelKey > 0) {
            cache.removeViewModel(viewModelKey, context);
        }
    }
}
