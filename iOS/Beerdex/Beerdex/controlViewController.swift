//
//  controlViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-27.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit
import Alamofire

private let reuseIdentifier = "BeerCell"

class controlViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    typealias imageMetaData = (Data?) -> ()
    
    @IBOutlet weak var collection: UICollectionView!
    
    
    private var selectedImage: UIImageView?
    private var downloadedImages = 0
    private var beersArray: [Beer]? {
        didSet {
            print("Set")
            DispatchQueue.main.async {
                self.collection.reloadData()
            }
        }
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        getDataFromServer()
        uploadImage()
    }

    func setupView() {
        self.view.backgroundColor = .white
        collection.backgroundColor = .white
        
    }
    
    func getDataFromServer() {
        downloadData() { (data) in
            guard let data = data else {
                print("Download failed")
                return
            }
            do {
                let beer = try
                    JSONDecoder().decode([Beer].self, from: data)
                self.beersArray = beer
            } catch let jsonerr {
                print("Error", jsonerr)
            }
        }
    }
    
    func downloadData(with completionHandler: @escaping imageMetaData ) {
        
        let todoEndpoint = "http://188.166.170.111:8080/stream"
        guard let url = URL(string: todoEndpoint) else { return }
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = "POST"
        urlRequest.setValue("Application/json", forHTTPHeaderField: "Content-Type")
        
        guard let body = try? JSONSerialization.data(withJSONObject: ["lastImageID":downloadedImages], options: []) else {
            return
        }
        urlRequest.httpBody = body
        
        let task = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in
            
            if let error = error {
                print(error)
            }
            completionHandler(data)
        }
        task.resume()
    }
    
    func uploadImage() {
        // TODO: Add image picker
        guard let imageToUpload = UIImageJPEGRepresentation(#imageLiteral(resourceName: "impstout"), 1) else { print ("Failed to serialize image"); return }
        
        // TODO: Add endpoint
        guard let uploadurl = URL(string: "http://188.166.170.111:8080/image/upload") else { return }
        
        var request = URLRequest(url: uploadurl)
        request.httpMethod = "POST"
        request.setValue("Application/json", forHTTPHeaderField: "Content-Type")
        guard let body = try? JSONSerialization.data(withJSONObject: ["image":"impstout", "user": 1, "beer": 1, "description": "Test desc"], options: []) else {
            return
        }
        request.httpBody = body
        print ("TEST")
        
        let configuration = URLSessionConfiguration.default
        let session = URLSession(configuration: configuration, delegate: self as? URLSessionDelegate, delegateQueue: OperationQueue.main)
        let task = session.uploadTask(with: request, from: imageToUpload)
        
        task.resume()
    }
    
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return beersArray?.count ?? 0
    }
    
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! BeerCell
        
        cell.beer = beersArray?[indexPath.item]
        cell.configure()
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        let selectedCell = collection.cellForItem(at: indexPath) as! BeerCell
        guard let cellImageView = selectedCell.imageTitle else { return }
        self.selectedImage = cellImageView
        
        performSegue(withIdentifier: "showSingleBeer", sender: Any?.self)
    }
    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSingleBeer" {
            let dest = segue.destination as! singleBeerViewController
            dest.singleImage = (self.selectedImage?.image)!
            // TODO: Set destination data such as the proper image
        }
    }
    

}
