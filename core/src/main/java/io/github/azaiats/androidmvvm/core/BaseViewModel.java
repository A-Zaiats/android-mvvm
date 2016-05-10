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

package io.github.azaiats.androidmvvm.core;

import android.databinding.BaseObservable;
import android.support.annotation.CallSuper;

/**
 * ViewModel base class. Every ViewModel must extend this class.
 *
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public abstract class BaseViewModel extends BaseObservable implements MvvmViewModel {

    private boolean running;

    /**
     * Called when this ViewModel instance was created.
     * <p>
     * This is a place to do any initialisation.
     */
    @Override
    @CallSuper
    public void onCreate() { }

    /**
     * Called when this ViewModel was binded to a view and the view is visible.
     */
    @Override
    @CallSuper
    public void onResume() {
        running = true;
    }

    /**
     * Called when this ViewModel was unbinded from a view or view was paused.
     * <p>
     * Don't interact with view after this method was called
     * (e.g. show toast or start new activity).
     */
    @Override
    @CallSuper
    public void onPause() {
        running = false;
    }

    /**
     * Called when this ViewModel instance was destroyed and removed from cache.
     * <p>
     * This is a place to do any cleanup to avoid memory leaks.
     */
    @Override
    @CallSuper
    public void onDestroy() { }

    /**
     * Returns true if this ViewModel is attached to view end the view is in running state (not paused).
     *
     * @return true if running
     */
    protected boolean isRunning() {
        return running;
    }
}
