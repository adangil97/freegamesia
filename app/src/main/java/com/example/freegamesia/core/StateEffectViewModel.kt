package com.example.freegamesia.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

typealias NoActions = Unit

/**
 * Simple MVI ViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class StateEffectViewModel<State, Action>(
    initialState: State,
    initialAction: Action? = null
) : ViewModel() {
    protected val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = mutableState.asStateFlow()
    private val mutableActions = MutableStateFlow(initialAction)
    private val actions = mutableActions.asStateFlow()

    protected fun <ResultType> fetchFromActions(
        request: suspend (Action) -> Flow<ResultType>,
        response: suspend (ResultType) -> Unit,
    ) {
        viewModelScope.launch {
            actions.flatMapLatest { actionEvent ->
                actionEvent?.let {
                    request(it)
                } ?: flowOf()
            }.collectLatest(response)
        }
    }

    fun sendAction(
        action: Action? = currentAction()
    ) {
        viewModelScope.launch {
            mutableActions.value = action
        }
    }

    fun currentAction() = mutableActions.value

    protected fun currentState() = mutableState.value
}