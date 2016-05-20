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

package io.github.azaiats.androidmvvm.core.mocks;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingActivityDelegate;
import io.github.azaiats.androidmvvm.core.delegates.NavigatingActivityDelegateCallback;

/**
 * @author Andrei Zaiats
 */
public class TestNavigatingActivityDelegate extends NavigatingActivityDelegate {
    /**
     * Create delegate for activity.
     *
     * @param callback          the ActivityDelegateCallback for this delegate
     * @param delegatedActivity the Activity for delegation
     */
    public TestNavigatingActivityDelegate(@NonNull NavigatingActivityDelegateCallback callback,
                                          @NonNull Activity delegatedActivity) {
        super(callback, delegatedActivity);
    }

    // DataBindingUtils can't be mocked
    @Override
    protected ViewDataBinding initBinding(BindingConfig bindingConfig) {
        return null;
    }
}
