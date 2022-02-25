package kmp.redux.android.Views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kmp.redux.android.SpaceStore
import kmp.redux.features.space.redux.SpaceAction
import kmp.redux.features.space.redux.SpaceState

@Preview(showBackground = true)
@Composable
fun SpaceView() {
    val state by SpaceStore.current.state.collectAsState()
    val dispatch = SpaceStore.current.dispatch

    Log.d(null, "SpaceView render")
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = when (state.status) {
                SpaceState.Status.IDLE -> "${state.peopleInSpace?.number.toString()} people in space !"
                SpaceState.Status.PENDING -> "Loading ..."
            }
        )
        Button(onClick = {
            dispatch(SpaceAction.FetchPeopleInSpaceSlow)
        }) {
            Text(text = "Reload Slow")
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        if (state.peopleInSpace == null) {
            dispatch(SpaceAction.FetchPeopleInSpace)
        }
    })
}