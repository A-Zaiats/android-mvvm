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
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;

/**
 * @author Andrei Zaiats
 */
public class TestMvvmDelegate extends MvvmDelegate {

    private boolean finished;
    private MvvmViewModel cachedViewModel;

    /**
     * Create a delegate.
     *
     * @param callback the {@link DelegateCallback} for this delegate
     */
    public TestMvvmDelegate(DelegateCallback callback) {
        super(callback);
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    // for tests
    void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Nullable
    @Override
    protected MvvmViewModel getCachedViewModel() {
        return cachedViewModel;
    }

    // for tests
    void setCachedViewModel(MvvmViewModel cachedViewModel) {
        this.cachedViewModel = cachedViewModel;
    }

    // Can't be mocked
    @NonNull
    @Override
    protected ViewDataBinding createDataBinding(@LayoutRes int layoutResource) {
        return null;
    }

    // DataBindingUtils can't be mocked
    @Override
    protected ViewDataBinding initBinding(BindingConfig bindingConfig) {
        return null;
    }
}
