//
//  UploadViewController.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-03-12.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import UIKit
import Alamofire

class UploadViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
   
    @IBOutlet weak var descriptionView: UITextView!
    @IBOutlet weak var beerTypeField: UITextField!
    
    @IBAction func selectImageButtonPressed(_ sender: Any) {
        openImageLib()
    }
    
    @IBAction func selectCameraButtonPressed(_ sender: Any) {
    }
    
    
    let pickerView = UIPickerView()
    var beerTypes = [BeerType]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        beerTypeField.placeholder = "Select beer type"
        pickerView.delegate = self
        pickerView.dataSource = self
        beerTypeField.inputView = pickerView
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
    
    // TODO: No hardcoded information
    func uploadBeerData(image: UIImage) {
        let url = "http://188.166.170.111:8080/image/upload"
        let imageData = UIImageJPEGRepresentation(image, 1.0)!
        let parameters = ["beerID":"1", "userID":"1", "description":"The very greatest"]
        
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
                        // TODO: Present alert that image has been uploaded
                    }
                case .failure(let encodingError):
                    print(encodingError)
                }
        })
    }

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return beerTypes.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return beerTypes[row].Name
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        beerTypeField.text = beerTypes[row].Name
        beerTypeField.resignFirstResponder()
    }
}
