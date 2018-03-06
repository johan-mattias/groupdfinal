//
//  singleBeerViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-27.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit

class singleBeerViewController: UIViewController, UITextViewDelegate {

    var singleImage = UIImage()
    var text = String()
    @IBOutlet weak var beerImage: UIImageView!
    
    @IBOutlet weak var beerDescription: UILabel!
    
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.beerImage.image = singleImage
        beerDescription.text = text
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
