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

package io.github.azaiats.androidmvvm.core.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Andrei Zaiats
 */
public class BaseViewModelTest {

    private TestViewModel viewModel;

    @Before
    public void init() {
        viewModel = new TestViewModel();
    }

    @Test
    public void testIsNotRunningWhenCreated() {
        viewModel.onCreate();
        assertFalse(viewModel.isRunning());
    }

    @Test
    public void testIsRunningWhenResumed() {
        viewModel.onCreate();
        viewModel.onResume();
        assertTrue(viewModel.isRunning());
    }

    @Test
    public void testIsNotRunningWhenPaused() {
        viewModel.onCreate();
        viewModel.onResume();
        viewModel.onPause();
        assertFalse(viewModel.isRunning());
    }

    private class TestViewModel extends BaseViewModel {

    }
}
