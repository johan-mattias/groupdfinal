//
//  NavigationViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-17.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit

class NavigationViewController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationBar.topItem?.title = "Beerdex"
        self.navigationBar.isTranslucent = true
        self.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationBar.shadowImage = UIImage()
        self.navigationBar.backgroundColor = UIColor.clear
        
    }
}
