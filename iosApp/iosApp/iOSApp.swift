import SwiftUI
import shared

@main
struct iOSApp: App {
    let store: AppStore
    let spaceStore: SpaceStore
    let counterStore: CounterStore
    
	var body: some Scene {
        WindowGroup {
            RootView()
                .environmentObject(store)
                .environmentObject(spaceStore)
                .environmentObject(counterStore)
                .onAppear {
                    // warm up kotlin
                    ViewControllersKt.getSpaceViewController().loadView()
                }
        }
	}
    
    init() {
        store = AppStore()
        spaceStore = store.derived(deriveState: \.space, embedAction: {( action: SpaceActionKs) -> AppActionKs in AppActionKs.space(.init(action: action.sealed)) })
        counterStore = store.derived(deriveState: \.counter, embedAction: {( action: CounterActionKs) -> AppActionKs in AppActionKs.counter(.init(action: action.sealed)) })
    }
}

typealias SpaceStore = DerivedStore<SpaceState, SpaceActionKs>
typealias CounterStore = DerivedStore<Int32, CounterActionKs>
