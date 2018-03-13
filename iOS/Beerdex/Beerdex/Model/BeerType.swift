//
//  BeerType.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-03-12.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation

class BeerType: Codable {
    let id: Int
    let Name: String
    let beerTypeID: Int
    let breweryID: Int
    let countryID: Int
}

public enum BeerList {
    case Lager
    
    var id: Int {
        switch self {
            case .Lager: return 1
        }
        
        
        
    }
}
