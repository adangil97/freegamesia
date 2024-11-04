package com.example.freegamesia.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

typealias NoActions = Unit

typealias NoEffects = Unit

/**
 * Simple MVI ViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class StateEffectsViewModel<State, Effect, Action>(
    initialState: State,
    initialAction: Action? = null
) : ViewModel() {
    // To perform ui state.
    protected val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = mutableState.asStateFlow()

    // To performs ui actions
    private val mutableActions = MutableStateFlow(initialAction)
    private val actions = mutableActions.asStateFlow()

    // To perform ui effects like a snack-bar, toast or similar.
    private val mutableEffects = MutableStateFlow<Effect?>(null)
    val effects = mutableEffects.asStateFlow()

    protected fun <ResultType> fetchFromActions(
        request: suspend (Action) -> Flow<ResultType>,
        response: suspend (ResultType) -> Unit,
    ) {
        viewModelScope.launch {
            actions.flatMapLatest { actionEvent ->
                actionEvent?.let {
                    request(it)
                } ?: flowOf()
            }.collect(response)
        }
    }

    protected fun launchEffect(
        effect: Effect,
        withDelay: Long? = 100
    ) {
        viewModelScope.launch {
            mutableEffects.value = effect
            withDelay?.let {
                delay(it)
            }
            mutableEffects.value = null
        }
    }

    fun sendAction(action: Action? = currentAction()) {
        viewModelScope.launch {
            mutableActions.value = action
        }
    }

    fun currentAction() = mutableActions.value

    fun updateState(newState: State) {
        mutableState.value = newState
    }

    protected fun currentState() = mutableState.value
}