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
            print("Set beer array")
            DispatchQueue.main.async {
                self.collection.reloadData()
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        getDataFromServer()
    }

    func setupView() {
        self.view.backgroundColor = .white
        collection.backgroundColor = .white
    }

    func getDataFromServer() {
        downloadBeerMetaData() { (data) in
            guard let data = data else {
                print("Download failed")
                return
            }
            do {
                let beer = try JSONDecoder().decode([Beer].self, from: data)
                self.beersArray = beer
            } catch let jsonerr {
                print("Error", jsonerr)
            }
        }
    }
    
    func downloadBeerMetaData(with completionHandler: @escaping imageMetaData ) {
        
        let urlRequest = BeerRouter.getAll.asURLRequest()
        
        let task = URLSession.shared.dataTask(with: urlRequest) { (data, response, error) in

            if let error = error {
                print(error)
            }
            if let response = response {
                print(response)
            }
            if let data = data {
                completionHandler(data)
            }
        }
        task.resume()
    }
    
    func uploadBeer() {
        let url = "http://188.166.170.111:8080/image/upload"
        let image = #imageLiteral(resourceName: "impstout")
        let imageData = UIImageJPEGRepresentation(image, 1.0)!
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                multipartFormData.append(imageData, withName: "image", fileName: "impstout.jpg", mimeType: "image/jpeg")
        },
            to: url,
            encodingCompletion: { encodingResult in
                switch encodingResult {
                case .success(let upload, _, _):
                    upload.responseString { response in
                        debugPrint(response)
                    }
                case .failure(let encodingError):
                    print(encodingError)
                }
        }
        )}
    
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
