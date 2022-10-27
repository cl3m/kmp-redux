//
//  RootView.swift
//  KMP Redux
//
//  Created by Clément Beffa on 20.02.22.
//

import SwiftUI

struct RootView: View {
    var body: some View {
        TabView {
            SpaceView()
                .tabItem {
                    Image(systemName: "sparkles")
                    Text("Space")
                }
            
            CounterView()
                .tabItem {
                    Image(systemName: "number")
                    Text("Counter")
                }
            
            ComposeView()
                .tabItem {
                    Image(systemName: "number")
                    Text("Compose")
                }
        }
    }
}

struct RootView_Previews: PreviewProvider {
    static var previews: some View {
        RootView()
    }
}
