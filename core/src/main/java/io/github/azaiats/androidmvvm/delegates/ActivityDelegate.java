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

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.common.MvvmViewModel;

/**
 * A delegate for Activities lifecycle.
 * <p>
 * The following methods must be invoked from the corresponding Activities lifecycle methods:
 * <ul>
 * <li>{@link #onCreate(android.os.Bundle)}
 * <li>{@link #onSaveInstanceState(android.os.Bundle)} ()}
 * <li>{@link #onDestroy()}
 * </ul>
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public class ActivityDelegate<T extends ViewDataBinding, S extends MvvmViewModel>
        extends MvvmDelegate<T, S> {

    private Activity delegatedActivity;

    /**
     * Create delegate for activity.
     *
     * @param callback          the {@link DelegateCallback} for this delegate
     * @param delegatedActivity the {@link Activity} for delegation
     */
    public ActivityDelegate(@NonNull DelegateCallback<T, S> callback, @NonNull Activity delegatedActivity) {
        super(callback, delegatedActivity);
        this.delegatedActivity = delegatedActivity;
    }

    @NonNull
    @Override
    protected T createDataBinding(@LayoutRes int layoutResource) {
        return DataBindingUtil.setContentView(delegatedActivity, layoutResource);
    }
}
