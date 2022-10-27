package kmp.redux

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.useContents
import platform.UIKit.*
import androidx.compose.ui.window.Application
import kmp.redux.features.counter.CounterAction
import kmp.redux.features.counter.CounterState
import kmp.redux.features.space.redux.SpaceAction
import kmp.redux.features.space.redux.SpaceState
import kmp.redux.redux.AppAction
import kmp.redux.redux.AppStore

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

// ViewController is rebuild every time. It should not be the case but the compose layer is disposed :
// https://github.com/JetBrains/androidx/blob/f1e595b90421e4896a9164c3ae0a652920020b77/compose/ui/ui/src/uikitMain/kotlin/androidx/compose/ui/uikit/ComposeWindow.uikit.kt

open class HostingController(val controller: UIViewController) {
    val height = mutableStateOf(300.dp)
    val width = mutableStateOf(300.dp)

    init {
        //controller.view.backgroundColor = UIColor.blueColor
    }

    fun viewWillAppear() {
        val demoViewController = Application("Falling Balls") {
            val AppStore = compositionLocalOf { AppStore.shared }
            val SpaceStore = compositionLocalOf { kmp.redux.redux.AppStore.shared.derived<SpaceState, SpaceAction>({ it.space }, { AppAction.Space(it) }) }
            val CounterStore = compositionLocalOf { kmp.redux.redux.AppStore.shared.derived<CounterState, CounterAction>({ it.counter }, { AppAction.Counter(it) }) }

            Box(modifier = Modifier.size(remember { width }.value, remember { height }.value)) {
                val state by CounterStore.current.state.collectAsState()
                val dispatch = CounterStore.current.dispatch

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
                ) {
                    Text(
                        text = "$state"
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(50.dp)
                    ) {
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
        }

        controller.view.addSubview(demoViewController.view)
        demoViewController.view.setFrame(controller.view.bounds)
        height.value = controller.view.bounds.useContents { size.height }.toFloat().dp
        width.value = controller.view.bounds.useContents { size.width }.toFloat().dp
        controller.addChildViewController(demoViewController)
    }

    fun viewDidDisappear() {

    }
}