//
//  Todo.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-02-12.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation

struct Beer: Codable {
    let userId: Int
    let id: Int
    let title: String
    let body: String
}
