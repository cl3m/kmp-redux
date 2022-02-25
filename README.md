[![Kotlin Multiplatform](https://img.shields.io/static/v1?logo=Kotlin&&logoColor=3c94cf&label=&message=Kotlin%20Multiplatform&color=555)](https://kotlinlang.org/docs/reference/multiplatform.html)

# KMP Redux

Basic redux setup for kotlin multiplatform with JetPack Compose and SwiftUI. AppStore can be derived into sub store.

## Libraries

- kotlinx coroutines
- kotlinx serialization
- ktor
- [KSwift](https://github.com/icerockdev/moko-kswift)
- [KMP Native coroutines](https://github.com/rickclephas/KMP-NativeCoroutines)

## Redux Usage

[CounterStore](https://github.com/cl3m/kmp-redux/blob/develop/shared/src/commonMain/kotlin/kmp/redux/features/counter/CounterRedux.kt) usage on Jetpack Compose

```kotlin
@Composable
fun CounterView() {
    val state by CounterStore.current.state.collectAsState()
    val dispatch = CounterStore.current.dispatch

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
```

[CounterStore](https://github.com/cl3m/kmp-redux/blob/develop/shared/src/commonMain/kotlin/kmp/redux/features/counter/CounterRedux.kt) usage on SwiftUI

```swift
struct CounterView: View {
    @EnvironmentObject private var store: CounterStore

    var body: some View {
        return VStack(spacing: 20) {
            Text("\(store.state)")
            HStack(spacing: 50) {
                Button("+") {
                    store.dispatch(.increment)
                }
                Button("-") {
                    store.dispatch(.decrement)
                }
            }
        }
    }
}
```

## Inspiration

Space store inspired from [KMP Mobile Template](https://github.com/xorum-io/kmp_mobile_template)
