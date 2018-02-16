//
//  BeerCollectionViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-15.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit

private let reuseIdentifier = "BeerCell"

class BeerCollectionViewController: UICollectionViewController {
    
    var testBeer: [Beer]? {
        didSet {
            guard let beer = testBeer else { return }
            for x in beer {
                print (x.title)
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getDataFromServer()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    //MARK: - Data
    
    func getDataFromServer() {
        downloadData() { (data) in
            guard let data = data else {
                print("Download failed")
                return
            }
            do {
                let beer = try
                    JSONDecoder().decode([Beer].self, from: data)
                    self.testBeer = beer
            } catch let jsonerr {
                print("Error", jsonerr)
            }
        }
    }
    
    func downloadData(completionHandler: @escaping (Data?) -> () ) {
        
        let todoEndpoint: String = "https://jsonplaceholder.typicode.com/posts"
        guard let url = URL(string: todoEndpoint) else { return }
        let urlRequest = URLRequest(url: url)
        let session = URLSession.shared
        
        let task = session.dataTask(with: urlRequest) { (data, response, error) in
            if let response = response {
                print(response)
            }
            if let error = error {
                print(error)
            }
            completionHandler(data)
        }
        task.resume()
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

    // MARK: UICollectionViewDataSource

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }


    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return 5
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! BeerCell
    
        // Configure the cell
        cell.setup()
        return cell
    }

    // MARK: UICollectionViewDelegate

    /*
    // Uncomment this method to specify if the specified item should be highlighted during tracking
    override func collectionView(_ collectionView: UICollectionView, shouldHighlightItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment this method to specify if the specified item should be selected
    override func collectionView(_ collectionView: UICollectionView, shouldSelectItemAt indexPath: IndexPath) -> Bool {
        return true
    }
    */

    /*
    // Uncomment these methods to specify if an action menu should be displayed for the specified item, and react to actions performed on the item
    override func collectionView(_ collectionView: UICollectionView, shouldShowMenuForItemAt indexPath: IndexPath) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, canPerformAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) -> Bool {
        return false
    }

    override func collectionView(_ collectionView: UICollectionView, performAction action: Selector, forItemAt indexPath: IndexPath, withSender sender: Any?) {
    
    }
    */

}
