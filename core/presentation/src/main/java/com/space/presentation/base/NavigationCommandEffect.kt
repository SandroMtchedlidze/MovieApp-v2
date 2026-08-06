package com.space.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.space.navigation.requireGlobalNavigator

@Composable
fun <State, Event> NavigationCommandEffect(
    viewModel: BaseViewModel<State, Event>
) {
    val navigator = requireGlobalNavigator()
    LaunchedEffect(viewModel) {
        viewModel.navigationCommands.collect { command ->
            command.execute(navigator)
        }
    }
}