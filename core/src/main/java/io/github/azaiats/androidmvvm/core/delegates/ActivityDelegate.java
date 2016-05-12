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

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * A delegate for Activities lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate()}
 * <li>{@link #onResume()}
 * <li>{@link #onPause()}
 * <li>{@link #onDestroy()}
 * <li>{@link #onRetainCustomNonConfigurationInstance()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public class ActivityDelegate<T extends ViewDataBinding, S extends MvvmViewModel> {

    private static final String BINDING_VARIABLE_ERROR_TEXT = "Binding variable wasn't set successfully. "
            + "Probably viewModelVariableName of your BindingConfig of %s doesn't match any variable in %s.";

    private final ActivityDelegateCallback<T, S> callback;
    private final Activity delegatedActivity;
    private S viewModel;

    /**
     * Create delegate for activity.
     *
     * @param callback          the {@link ActivityDelegateCallback} for this delegate
     * @param delegatedActivity the {@link Activity} for delegation
     */
    public ActivityDelegate(@NonNull ActivityDelegateCallback<T, S> callback, @NonNull Activity delegatedActivity) {
        this.callback = callback;
        this.delegatedActivity = delegatedActivity;
    }

    /**
     * This method must be called from {@link Activity#onCreate(android.os.Bundle)}
     */
    @SuppressWarnings("unchecked")
    public void onCreate() {
        final MvvmView<T, S> view = callback.getView();
        final BindingConfig bindingConfig = view.getBindingConfig();
        viewModel = (S) callback.getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            viewModel = callback.createViewModel();
        }

        T binding = DataBindingUtil.setContentView(delegatedActivity, bindingConfig.getLayoutResource());
        if (!binding.setVariable(bindingConfig.getViewModelVariableName(), viewModel)) {
            throw new IllegalArgumentException(
                    String.format(BINDING_VARIABLE_ERROR_TEXT,
                            view.getClass().getSimpleName(),
                            binding.getClass().getSimpleName()
                    ));
        }

        view.setViewModel(viewModel);
        view.setBinding(binding);
        viewModel.onCreate();
    }

    /**
     * This method must be called from {@link Activity#onResume()}
     */
    public void onResume() {
        viewModel.onResume();
    }

    /**
     * This method must be called from {@link Activity#onPause()}
     */
    public void onPause() {
        viewModel.onPause();
    }

    /**
     * This method must be called from {@link Activity#onDestroy()}
     */
    public void onDestroy() {
        if (viewModel == null) return;

        if (delegatedActivity.isFinishing()) {
            viewModel.onDestroy();
        }
    }

    /**
     * This method must be called from
     * {@link android.support.v4.app.FragmentActivity#onRetainCustomNonConfigurationInstance()}.
     * Don't forget to return the value returned by this delegate method.
     *
     * @return the {@link MvvmViewModel} for bind it in restored activity
     */
    @Nullable
    public Object onRetainCustomNonConfigurationInstance() {
        if (!delegatedActivity.isChangingConfigurations()) {
            viewModel.onDestroy();
            viewModel = null;
        }
        return viewModel;
    }
}
