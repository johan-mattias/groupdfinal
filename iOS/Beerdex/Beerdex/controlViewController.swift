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

class controlViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {

    @IBOutlet weak var collection: UICollectionView!
    
    var downloadedImages = 0
    var beersArray: [Beer]? {
        didSet {
            print("Set")
            downloadedImages += (beersArray?.count)!
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
    
    func downloadData(completionHandler: @escaping (Data?) -> () ) {
        
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
        performSegue(withIdentifier: "showSingleBeer", sender: Any?.self)
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSingleBeer" {
            
        }
        // Pass the selected object to the new view controller.
    }
    

}