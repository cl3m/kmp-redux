package kmp.redux.redux

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppStore {
    companion object {
        val shared = AppStore()
    }
    private val stateFlow = MutableStateFlow(AppState())
    lateinit var dispatch: (AppAction) -> Unit

    init {
        dispatch = {
            stateFlow.value = appReducer(stateFlow.value, it) {
            this.dispatch(it)
        }
    }
    }

    val state: StateFlow<AppState>
        get() = stateFlow

    fun <DerivedState, ExtractedAction> derived(deriveState: (AppState) -> DerivedState, embedAction: (ExtractedAction) -> AppAction): DerivedStore<DerivedState, ExtractedAction> {
        val store = DerivedStore<DerivedState, ExtractedAction>(deriveState(state.value)) {
            dispatch(embedAction(it))
        }

        MainScope().launch {
            state.collect {
                store.stateFlow.value = deriveState(it)
            }
        }

        return store
    }
}

class DerivedStore<State, Action>(initialState: State, val dispatch: (Action) -> Unit) {
    internal val stateFlow: MutableStateFlow<State> = MutableStateFlow(initialState)

    val state: StateFlow<State>
        get() = stateFlow
}