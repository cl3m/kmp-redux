package kmp.redux.ui

import androidx.compose.runtime.*
import androidx.compose.ui.window.Application
import kmp.redux.redux.AppAction
import kmp.redux.redux.AppStore
import kmp.redux.redux.ui.CounterStore
import kmp.redux.redux.ui.CounterView
import kmp.redux.redux.ui.SpaceStore
import kmp.redux.redux.ui.SpaceView

fun getCounterViewController() = Application {
    CounterStore = compositionLocalOf { AppStore.shared.derived({ it.counter }, { AppAction.Counter(it) }) }
    CounterView()
}

fun getSpaceViewController() = Application {
    SpaceStore = compositionLocalOf { AppStore.shared.derived({ it.space }, { AppAction.Space(it) }) }
    SpaceView()
}