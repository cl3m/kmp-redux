//
//  ComposeView.swift
//  iosApp
//
//  Created by Clément Beffa on 24.02.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    let hostingController = HostingController(controller: HostingViewController())
    typealias UIViewControllerType = UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        (hostingController.controller as? HostingViewController)?.hostingController = hostingController
        return hostingController.controller
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
    }
}

class HostingViewController: UIViewController {
    var hostingController: HostingController?
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        hostingController?.viewWillAppear()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        hostingController?.viewDidDisappear()
        for view in view.subviews {
            view.removeFromSuperview()
        }        
    }
    
}
