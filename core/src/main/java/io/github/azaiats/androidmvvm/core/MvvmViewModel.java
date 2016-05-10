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

/**
 * The root ViewModel interface for every mvvm ViewModel.
 *
 * @author Andrei Zaiats
 * @since 0.1.0
 */
public interface MvvmViewModel {

    /**
     * Called when this ViewModel instance was created.
     * <p>
     * This is a place to do any initialisation.
     */
    void onCreate();

    /**
     * Called when this ViewModel was binded to a view and the view is visible.
     */
    void onResume();

    /**
     * Called when this ViewModel was unbinded from a view or view was paused.
     * <p>
     * Don't interact with view after this method was called
     * (e.g. show toast or start new activity).
     */
    void onPause();

    /**
     * Called when this ViewModel instance was destroyed and removed from cache.
     * <p>
     * This is a place to do any cleanup to avoid memory leaks.
     */
    void onDestroy();
}
