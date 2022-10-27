//
//  ComposeSpaceView.swift
//  iosApp
//
//  Created by Clem on 27.10.22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ComposeSpaceView: UIViewControllerRepresentable {
    typealias UIViewControllerType = UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        return AvoidDispose(ViewControllersKt.getSpaceViewController())
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
    }
}
