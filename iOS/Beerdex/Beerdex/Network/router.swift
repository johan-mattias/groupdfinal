//
//  router.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-03-05.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation
import Alamofire

public enum routerEndpoint {
    case getAll
    case upload
}

open class RewardRouter: BaseRouter {
    
    var endpoint: routerEndpoint
    
    public init(endpoint: routerEndpoint) {
        self.endpoint = endpoint
    }
    
    override open var method: HTTPMethod {
        switch endpoint {
        case .getAll: return .post
        case .upload: return .post
        }
    }
    
    override open var path: String {
        switch endpoint {
        case .getAll: return "stream"
        case .upload: return "image/upload"
        }
    }
    
    override open var parameters: Parameters? {
        
        switch endpoint {
        case .getAll: return ["lastImageID":"0"]
        case .upload: return ["beerID":"1", "userID":"1", "description":"ANYTHING!"]
        }
        
    }
    
    override open var encoding: ParameterEncoding? {
        return URLEncoding.httpBody
    }
}

