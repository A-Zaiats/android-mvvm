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
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
public class ActivityDelegate<T extends ViewDataBinding, S extends MvvmViewModel>
        extends MvvmDelegate<T, S> {

    private final ActivityDelegateCallback<T, S> callback;
    private final Activity delegatedActivity;

    /**
     * Create delegate for activity.
     *
     * @param callback          the {@link ActivityDelegateCallback} for this delegate
     * @param delegatedActivity the {@link Activity} for delegation
     */
    public ActivityDelegate(@NonNull ActivityDelegateCallback<T, S> callback, @NonNull Activity delegatedActivity) {
        super(callback);
        this.callback = callback;
        this.delegatedActivity = delegatedActivity;
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
        if (!delegatedActivity.isChangingConfigurations() && viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
        return viewModel;
    }

    @Override
    protected boolean isFinished() {
        return !delegatedActivity.isChangingConfigurations();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected S getCachedViewModel() {
        return (S) callback.getLastCustomNonConfigurationInstance();
    }

    @NonNull
    @Override
    protected T createDataBinding(@LayoutRes int layoutResource) {
        return DataBindingUtil.setContentView(delegatedActivity, layoutResource);
    }
}
