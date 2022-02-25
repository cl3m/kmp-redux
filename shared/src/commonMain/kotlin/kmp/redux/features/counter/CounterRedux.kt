package kmp.redux.features.counter

// State

typealias CounterState = Int

// Actions

sealed class CounterAction {
    object Increment : CounterAction()
    object Decrement : CounterAction()
}

// Reducers

@Suppress("UNUSED_PARAMETER")
fun counterReducer(state: CounterState, action: CounterAction, dispatch: (CounterAction) -> Unit): CounterState {
    return when (action) {
        is CounterAction.Increment ->
            state + 1
        is CounterAction.Decrement ->
            state - 1
    }
}