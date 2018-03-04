//
//  Todo.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-12.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation

class Beer: Codable {
    let image_id: Int
    let link: String
    let user_name: String
    let beer_name: String
    let beer_type: String
    let brewery_name: String
    let country: String
    let description: String
}
