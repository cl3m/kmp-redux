package kmp.redux.redux

import kmp.redux.features.counter.CounterAction
import kmp.redux.features.counter.CounterState
import kmp.redux.features.counter.counterReducer
import kmp.redux.features.space.redux.SpaceAction
import kmp.redux.features.space.redux.SpaceState
import kmp.redux.features.space.redux.spaceReducer

// State

data class AppState(
    val space: SpaceState = SpaceState(),
    val counter: CounterState = 0
)

// Actions

sealed class AppAction {
    data class Space(val action: SpaceAction) : AppAction()
    data class Counter(val action: CounterAction): AppAction()
}

// Reducers
fun appReducer(state: AppState, action: AppAction, dispatch: (AppAction) -> Unit): AppState {
    return when(action) {
        is AppAction.Space ->
            state.copy(space = spaceReducer(state.space, action.action) {
                dispatch(AppAction.Space(it))
            })
        is AppAction.Counter ->
            state.copy(counter = counterReducer(state.counter, action.action) {
                dispatch(AppAction.Counter(it))
            })
    }
}