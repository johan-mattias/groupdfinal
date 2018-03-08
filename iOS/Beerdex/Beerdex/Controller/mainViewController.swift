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

class mainViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {

    typealias imageMetaData = (Data?) -> ()
    
    @IBOutlet weak var collection: UICollectionView!
    
    @IBAction func pressedSelectImageButton(_ sender: UIButton) {
        openImageLib()
    }
    
    private var selectedImage: UIImageView?
    private var selectedDescription: String?
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
                print("Error: \(jsonerr)")
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
    
    func uploadBeerData(image: UIImage) {
        let url = "http://188.166.170.111:8080/image/upload"
        let imageData = UIImageJPEGRepresentation(image, 1.0)!
        let parameters = ["beerID":"1", "userID":"1", "description":"ANYTHING!"]
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                multipartFormData.append(imageData, withName: "image", fileName: "impstout.jpeg", mimeType: "image/jpeg")
                for (key, val) in parameters {
                    multipartFormData.append(val.data(using: String.Encoding.utf8)!, withName: key)
                }
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
        })
    }
    
    func openImageLib() {
        let controller = UIImagePickerController()
        controller.delegate = self
        controller.sourceType = .photoLibrary
        
        present(controller, animated: true, completion: nil)
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        uploadBeerData(image: image)
        dismiss(animated: true, completion: nil)
    }
    
   func numberOfSections(in collectionView: UICollectionView) -> Int {
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
        let description = selectedCell.textField.text
        self.selectedImage = cellImageView
        self.selectedDescription = description
        
        performSegue(withIdentifier: "showSingleBeer", sender: Any?.self)
    }
    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSingleBeer" {
            let dest = segue.destination as! singleBeerViewController
            dest.singleImage = (self.selectedImage?.image)!
            dest.descriptionText = self.selectedDescription!
        }
    }
    

}
