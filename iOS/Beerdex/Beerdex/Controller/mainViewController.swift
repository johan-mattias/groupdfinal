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

    typealias metaData = (Data?) -> ()
    
    @IBOutlet weak var collection: UICollectionView!
    
    @IBAction func reloadCollectionViewPressed(_ sender: UIButton) {
        imageDownloadIndex = 0
        getDataFromServer()
    }
    
    private var gradientLayer: CAGradientLayer!
    private var selectedImage: UIImageView?
    private var selectedDescription: String?
    private var imageDownloadIndex = 0
    private var beerTypes: [BeerType]?
    private var beersArray: [Beer]? {
        didSet {
            DispatchQueue.main.async {
                self.collection.reloadData()
            }
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
    }

    func setupView() {
        self.view.backgroundColor = .white
        collection.layer.cornerRadius = 20.0
        getDataFromServer()
        getBeerTypes()
    }
    
    func getBeerTypes() {
        downloadBeerTypes() { data in
            guard let data = data else {
                print ("Downloading beer types failed")
                return
            }
            do {
                let type = try JSONDecoder().decode([BeerType].self, from: data)
                self.beerTypes = type
            } catch let jsonError {
                print ("Error decoding: \(jsonError)")
            }
        }
    }

    func getDataFromServer() {
        downloadBeerMetaData() { (data) in
            guard let data = data else {
                print("Download failed")
                return
            }
            do {
                let beer = try JSONDecoder().decode([Beer].self, from: data)
                if self.beersArray != nil {
                    self.beersArray?.append(contentsOf: beer)
                } else {
                    self.beersArray = beer
                }
                
            } catch let jsonError {
                print("Error: \(jsonError)")
            }
        }
    }
    
    func downloadBeerTypes(with completionHandler: @escaping metaData) {
        
        let beerTypeRequest = BeerRouter.getBeerTypes.asURLRequest()
        
        let session = URLSession(configuration: .default)
        
        let beerTypeTask = session.dataTask(with: beerTypeRequest) { (data, response, error) in
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
        beerTypeTask.resume()
    }
    
    func downloadBeerMetaData(with completionHandler: @escaping metaData ) {
        
        let imageUrlRequest = BeerRouter.getAll.asURLRequest(from: imageDownloadIndex)
        
        let imageTask = URLSession.shared.dataTask(with: imageUrlRequest) { (data, response, error) in

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
        imageTask.resume()
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
    
    func insertion(count: Int) {
        collection.insertItems(at: [IndexPath(row: count - 1, section: 0)])
    }
    
    func collectionView(_ collectionView: UICollectionView, willDisplay cell: UICollectionViewCell, forItemAt indexPath: IndexPath) {
        guard let count = beersArray?.count else { return }
        
        if indexPath.item == count - 1 {
            imageDownloadIndex += 20
            getDataFromServer()
        }
    }
    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSingleBeer" {
            let dest = segue.destination as! singleBeerViewController
            dest.singleImage = (self.selectedImage?.image)!
            dest.descriptionText = self.selectedDescription!
        }
        
        if segue.identifier == "uploadSegue" {
            let dest = segue.destination as! UploadViewController
            guard let types = self.beerTypes else { return }
            dest.beerTypes = types
        }
    }
}
