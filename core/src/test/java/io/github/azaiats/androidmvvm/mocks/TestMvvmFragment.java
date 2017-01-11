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

package io.github.azaiats.androidmvvm.mocks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.azaiats.androidmvvm.MvvmFragment;
import io.github.azaiats.androidmvvm.common.BindingConfig;
import io.github.azaiats.androidmvvm.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.delegates.MvvmDelegate;

import static org.mockito.Mockito.mock;

/**
 * @author Andrei Zaiats
 */
public class TestMvvmFragment extends MvvmFragment {

    private MvvmViewModel viewModel = mock(MvvmViewModel.class);
    private MvvmDelegate delegate = mock(MvvmDelegate.class);

    @NonNull
    @Override
    public MvvmViewModel createViewModel() {
        return viewModel;
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(0);
    }

    @Override
    public MvvmDelegate getMvvmDelegate() {
        return delegate;
    }

    // DataBindingUtils can't be properly mocked
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }
}
