package kmp.redux.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.compositionLocalOf
import kmp.redux.android.Views.RootView
import kmp.redux.features.counter.CounterAction
import kmp.redux.features.counter.CounterState
import kmp.redux.features.space.redux.SpaceAction
import kmp.redux.features.space.redux.SpaceState
import kmp.redux.redux.AppAction
import kmp.redux.redux.AppStore

private val appStore = AppStore()
val AppStore = compositionLocalOf { appStore }
val SpaceStore = compositionLocalOf { appStore.derived<SpaceState, SpaceAction>({ it.space }, { AppAction.Space(it) }) }
val CounterStore = compositionLocalOf { appStore.derived<CounterState, CounterAction>({ it.counter }, { AppAction.Counter(it) }) }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootView()
        }
    }
}