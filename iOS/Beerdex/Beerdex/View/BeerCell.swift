//
//  BeerCell.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-15.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit
import Alamofire


class BeerCell: UICollectionViewCell {
    // TODO: Check if bad place for cache? Each cell gets one?
    let cache = NSCache<AnyObject, AnyObject>()
    var beer: Beer?
    let baseUrl =  "http://188.166.170.111:8080/getImage/"
    
    @IBOutlet weak var imageTitle: UIImageView!
    @IBOutlet weak var textField: UITextField!
    
    func configure() {
        
        self.textField.text = beer?.link
        self.backgroundColor = .white
        getImageForCell()
    }
    
    func getImageForCell() {
        
        guard let imageUrl = beer?.link,
            let url = URL(string: baseUrl + imageUrl) else { return }
        
        let imageUrlString = url
        
        if let imageFromCache = self.cache.object(forKey: url as AnyObject) as? UIImage {
            self.imageTitle.image = imageFromCache
            return
        }
        
        URLSession.shared.dataTask(with: url) { (data, response, error) in
            
            if let error = error {
                print(error)
                return
            }
            
            DispatchQueue.main.async {
                guard let imageToCache = UIImage(data: data!) else { return }
                
                if imageUrlString == url {
                    self.imageTitle.image = imageToCache
                }
                self.cache.setObject(imageToCache, forKey: url as AnyObject)
            }
        }.resume()
        
            
        
    }
}
