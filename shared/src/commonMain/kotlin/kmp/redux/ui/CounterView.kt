package kmp.redux.redux.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import kmp.redux.features.counter.CounterAction
import kmp.redux.features.counter.CounterState
import kmp.redux.redux.DerivedStore

lateinit var CounterStore: ProvidableCompositionLocal<DerivedStore<CounterState, CounterAction>>

@Composable
internal fun CounterView() {
    val state by CounterStore.current.state.collectAsState()
    val dispatch = CounterStore.current.dispatch

    Napier.d( "CounterView render")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Text(text = "$state")
        Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
            Button(onClick = {
                dispatch(CounterAction.Increment)
            }) {
                Text(text = "+")
            }
            Button(onClick = {
                dispatch(CounterAction.Decrement)
            }) {
                Text(text = "-")
            }
        }
    }
}