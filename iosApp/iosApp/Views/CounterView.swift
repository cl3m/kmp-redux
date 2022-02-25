//
//  CounterView.swift
//  KMP Redux
//
//  Created by Clément Beffa on 20.02.22.
//

import SwiftUI

struct CounterView: View {
    @EnvironmentObject private var store: CounterStore

    var body: some View {
        print("CounterView render")
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

struct CounterView_Previews: PreviewProvider {
    static var previews: some View {
        CounterView()
            .environmentObject(CounterStore(initialState: 14, dispatch: { _ in }))
    }
}
