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

package io.github.azaiats.androidmvvm.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Andrei Zaiats
 */
public class NavigatingViewModelTest {

    @Mock
    private Navigator navigator;

    @Mock
    private NavigationCommand<Navigator> command;

    private NavigatingViewModel<Navigator> viewModel;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        viewModel = getNavigatingViewModel();
    }

    @Test
    public void testNotExecuteCommandOnNavigatorSetterIfNavigatorNull() {
        viewModel.pendingCommand = command;
        viewModel.setNavigator(null);
        verifyZeroInteractions(command);
    }

    @Test
    public void testNoNullPointerOnNavigatorSetterIfCommandNull() {
        viewModel.pendingCommand = null;
        viewModel.setNavigator(navigator);
    }

    @Test
    public void testExecutePendingCommandOnNavigatorAttach() {
        viewModel.pendingCommand = command;
        viewModel.setNavigator(navigator);
        verify(command).execute(navigator);
    }

    @Test
    public void testNoNullPointerOnCommandExecutionIfCommandNull() {
        viewModel.setNavigator(navigator);
        viewModel.executeNavigationCommand(null);
    }

    @Test
    public void testNotExecuteCommandIfNavigatorDetached() {
        viewModel.setNavigator(null);
        viewModel.executeNavigationCommand(command);
        verifyZeroInteractions(command);
    }

    @Test
    public void testSaveCommandIfNavigatorNull() {
        viewModel.pendingCommand = null;
        viewModel.setNavigator(null);
        viewModel.executeNavigationCommand(command);
        assertSame(command, viewModel.pendingCommand);
    }

    @Test
    public void testCommandExecution() {
        viewModel.setNavigator(navigator);
        viewModel.executeNavigationCommand(command);
        verify(command).execute(navigator);
    }

    @Test
    public void testPendingCommandExecutedOnceOnly() {
        viewModel.pendingCommand = command;
        final Navigator anotherNavigator = mock(Navigator.class);
        viewModel.setNavigator(navigator);
        viewModel.setNavigator(anotherNavigator);
        verify(command, times(1)).execute(navigator);
    }

    private NavigatingViewModel<Navigator> getNavigatingViewModel() {
        return new NavigatingViewModel<Navigator>() {};
    }
}
