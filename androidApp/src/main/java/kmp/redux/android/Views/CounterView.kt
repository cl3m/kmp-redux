package kmp.redux.android.Views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.redux.android.CounterStore
import kmp.redux.features.counter.CounterAction

@Preview(showBackground = true)
@Composable
fun CounterView() {
    val state by CounterStore.current.state.collectAsState()
    val dispatch = CounterStore.current.dispatch

    Log.d(null, "CounterView render")
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