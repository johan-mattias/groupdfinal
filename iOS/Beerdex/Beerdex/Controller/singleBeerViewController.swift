//
//  singleBeerViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-27.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit

class singleBeerViewController: UIViewController, UITextViewDelegate {

    @IBOutlet weak var beerImage: UIImageView!
    @IBOutlet weak var beerDescription: UILabel!
    
    var singleImage = UIImage()
    var descriptionText = String()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.beerImage.image = singleImage
        beerDescription.text = descriptionText
    }
}
