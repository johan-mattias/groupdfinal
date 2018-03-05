//
//  BaseRouter.swift
//  Beerdex
//
//  Created by Adam Woods on 2018-03-05.
//  Copyright © 2018 Adam Woods. All rights reserved.
//

import Foundation
import Alamofire

protocol APIConfiguration {
    var method: HTTPMethod { get }
    var encoding: Alamofire.ParameterEncoding? { get }
    var path: String { get }
    var parameters: Parameters? { get }
    static var baseUrl: String { get }
}

open class BaseRouter: URLRequestConvertible, APIConfiguration {
    
    public init() {
        
    }
    
    open var method: HTTPMethod {
        fatalError("[\(Mirror(reflecting: self).description) - \( #function ))] Must be overridden in subclass")
    }
    
    open var encoding: ParameterEncoding? {
        fatalError("[\(Mirror(reflecting: self).description) - \( #function ))] Must be overridden in subclass")
    }
    
    open var path: String {
        fatalError("[\(Mirror(reflecting: self).description) - \( #function ))] Must be overridden in subclass")
    }
    
    open var parameters: Parameters? {
        fatalError("[\(Mirror(reflecting: self).description) - \( #function ))] Must be overridden in subclass")
    }
    
    open static var baseUrl: String {
            return "http://188.166.170.111:8080"
        }
    
    public func asURLRequest() throws -> URLRequest {
        let baseURL = URL(string: BaseRouter.baseUrl)!
        var mutableURLRequest = URLRequest(url: baseURL.appendingPathComponent(path))
        mutableURLRequest.httpMethod = method.rawValue
        
        if let encoding = encoding {
            do {
                let x = try encoding.encode(mutableURLRequest, with: parameters)
                print("BODY: \(String(describing: x.httpBody?.description))")
                return x
            } catch {
                debugPrint("error: \(error)")
            }
        }
        print(mutableURLRequest.httpBody!)
        return mutableURLRequest
    }
    
}
