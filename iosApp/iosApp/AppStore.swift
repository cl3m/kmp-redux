//
//  AppStore.swift
//  KMP Redux
//
//  Created by Clément Beffa on 20.02.22.
//

import Foundation
import shared
import Combine
import KMPNativeCoroutinesCombine

class AppStore: ObservableObject {
    @Published private(set) var state: AppState
    private let store = shared.AppStore()
    private var cancellable: AnyCancellable?
    
    
    init() {
        state = store.stateNativeValue
        cancellable = createPublisher(for: store.stateNative)
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: { _ in
                
            }, receiveValue: { state in
                self.state = state
            })
    }
    
    func dispatch(_ action: AppActionKs) {
        store.dispatch(action.sealed)
    }
        
    func derived<DerivedState: Equatable, ExtractedAction>(
        deriveState: @escaping (AppState) -> DerivedState,
        embedAction: @escaping (ExtractedAction) -> AppActionKs
    ) -> DerivedStore<DerivedState, ExtractedAction> {
        let store = DerivedStore<DerivedState, ExtractedAction>(
            initialState: deriveState(state),
            dispatch: { action  in
                self.dispatch(embedAction(action))
            }
        )

        $state
            .map(deriveState)
            .removeDuplicates()
            .receive(on: DispatchQueue.main)
            .assign(to: &store.$state)

        return store
    }
}

class DerivedStore<State, Action>: ObservableObject {
    @Published fileprivate(set) var state: State
    let dispatch: (Action) -> Void
    
    init(initialState: State, dispatch: @escaping (Action) -> Void) {
        self.state = initialState
        self.dispatch = dispatch
    }
}
