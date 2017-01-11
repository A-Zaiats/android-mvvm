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

package io.github.azaiats.androidmvvm;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.azaiats.androidmvvm.common.MvvmView;
import io.github.azaiats.androidmvvm.common.MvvmViewModel;
import io.github.azaiats.androidmvvm.delegates.DelegateCallback;
import io.github.azaiats.androidmvvm.delegates.MvvmDelegate;

/**
 * Base Fragment class that uses DataBinding and implements Model-View-ViewModel architecture.
 *
 * @param <T> the type of {@link ViewDataBinding}
 * @param <S> the type of binded {@link MvvmViewModel}
 * @author Andrei Zaiats
 * @since 0.2.0
 */
public abstract class MvvmFragment<T extends ViewDataBinding, S extends MvvmViewModel> extends Fragment
        implements MvvmView<T, S>, DelegateCallback<T, S> {

    private List<Observable.OnPropertyChangedCallback> onPropertyChangedCallbacks = new ArrayList<>();
    private MvvmDelegate<T, S> delegate;
    private T binding;
    private S viewModel;
    private boolean isStateSaved;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isStateSaved = false;
        getMvvmDelegate().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        removeOnPropertyChangedCallbacks();
        if (isFinishing()) {
            getMvvmDelegate().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        isStateSaved = true;
        getMvvmDelegate().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public MvvmView<T, S> getMvvmView() {
        return this;
    }

    /**
     * Getter for view binding
     *
     * @return the {@link ViewDataBinding} for this view
     */
    @NonNull
    public T getBinding() {
        if (binding == null) {
            throw new IllegalStateException("You can't call getBinding() before fragment creating.");
        }
        return binding;
    }

    @Override
    @CallSuper
    public void setBinding(@NonNull T binding) {
        this.binding = binding;
    }

    /**
     * Getter for binded ViewModel
     *
     * @return the {@link MvvmViewModel} binded to this view
     */
    @NonNull
    public S getViewModel() {
        if (viewModel == null) {
            throw new IllegalStateException("You can't call getViewModel() before fragment creating.");
        }
        return viewModel;
    }

    @Override
    @CallSuper
    public void setViewModel(@NonNull S viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Add an Observable.OnPropertyChangedCallback to ViewModel that will be removed on View destroy
     * <p>
     * Use this method to add Observable.OnPropertyChangedCallback to ViewModel. All callbacks will be removed on
     * View destroy. It helps avoid memory leaks via callbacks.
     *
     * @param callback the callback to start listening
     */
    protected void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        onPropertyChangedCallbacks.add(callback);
        getViewModel().addOnPropertyChangedCallback(callback);
    }

    /**
     * Create delegate for the current fragment if not exists.
     *
     * @return the {@link MvvmDelegate} for current fragment
     */
    protected MvvmDelegate getMvvmDelegate() {
        if (delegate == null) {
            delegate = new MvvmDelegate<>(this, getActivity());
        }
        return delegate;
    }

    private void removeOnPropertyChangedCallbacks() {
        for (Observable.OnPropertyChangedCallback callback : onPropertyChangedCallbacks) {
            getViewModel().removeOnPropertyChangedCallback(callback);
        }
    }

    private boolean isFinishing() {
        return !isStateSaved
                || isRemoving()
                || getActivity().isFinishing();
    }
}
