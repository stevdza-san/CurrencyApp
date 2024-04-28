import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        KoinModuleKt.initializeKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView().ignoresSafeArea()
		}
	}
}
