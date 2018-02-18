//
//  Todo.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-12.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation

class Beer: Codable {
//    let userId: Int
//    let id: Int
//    let title: String
//    let body: String
    let title: String?
    let number_of_views: Int?
    let thumbnail_image_name: String?
    let channel: Channel?
    let duration: Int?
}

class Channel: Codable {
    let name: String?
    let profile_image_name: String?
}
