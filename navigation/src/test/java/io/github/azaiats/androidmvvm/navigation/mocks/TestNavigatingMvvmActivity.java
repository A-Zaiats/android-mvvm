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

package io.github.azaiats.androidmvvm.navigation.mocks;

import android.support.annotation.NonNull;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.navigation.NavigatingMvvmActivity;
import io.github.azaiats.androidmvvm.navigation.common.Navigator;

import static org.mockito.Mockito.mock;

/**
 * @author Andrei Zaiats
 */
public class TestNavigatingMvvmActivity extends NavigatingMvvmActivity {

    private Navigator navigator = mock(Navigator.class);
    private MvvmViewModel viewModel = mock(MvvmViewModel.class);
    private BindingConfig bindingConfig = mock(BindingConfig.class);

    @NonNull
    @Override
    public Navigator getNavigator() {
        return navigator;
    }

    @NonNull
    @Override
    public MvvmViewModel createViewModel() {
        return viewModel;
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return bindingConfig;
    }
}
