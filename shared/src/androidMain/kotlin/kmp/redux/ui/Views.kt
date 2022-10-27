package kmp.redux.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import kmp.redux.features.counter.CounterAction
import kmp.redux.features.counter.CounterState
import kmp.redux.features.space.redux.SpaceAction
import kmp.redux.features.space.redux.SpaceState
import kmp.redux.redux.DerivedStore
import kmp.redux.redux.ui.CounterStore
import kmp.redux.redux.ui.SpaceStore
import kmp.redux.redux.ui.SpaceView
import kmp.redux.redux.ui.CounterView

@Composable
fun CounterView(counterStore: DerivedStore<CounterState, CounterAction>) {
    CounterStore = compositionLocalOf { counterStore }
    CounterView()
}

@Composable
fun SpaceView(spaceStore: DerivedStore<SpaceState, SpaceAction>) {
    SpaceStore = compositionLocalOf { spaceStore }
    SpaceView()
}