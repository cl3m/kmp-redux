import SwiftUI
import shared

struct SpaceView: View {
    @EnvironmentObject private var store: SpaceStore

    var peoplesInSpace: String {
        switch(store.state.status.enum) {
        case .idle:
            return "\(store.state.peopleInSpace?.number ?? 0) people in space !"
        case .pending:
            return  "Loading ..."
        }
    }
    
    var body: some View {
        print("SpaceView render")
        return VStack(spacing: 20) {
            Text(peoplesInSpace)
            Button("Reload Slow") {
                store.dispatch(.fetchPeopleInSpaceSlow)
            }
        }
        .onAppear {
            if store.state.peopleInSpace == nil {
                store.dispatch(.fetchPeopleInSpace)
            }
        }
    }
}

struct SpaceView_Previews: PreviewProvider {
    static var previews: some View {
        SpaceView()
            .environmentObject(SpaceStore(initialState: .init(peopleInSpace: PeopleInSpace(number: 20), status: .idle), dispatch: { _ in }))
    }
}
