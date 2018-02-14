var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {

	var rawjson = {};
    var key = 'stream';
    rawjson[key] = [];
    



    for(i=1;i<=50;i=i+2){

        var link1 = {
            id: i,
            link: 'https://res.cloudinary.com/teepublic/image/private/s--UvSUNgzW--/b_rgb:fffffe,t_Heather%20Preview/c_lpad,f_jpg,h_630,q_90,w_1200/v1494469473/production/designs/1595608_1.jpg'}
    
    
        var link2 = {
            id: i+1,
            link: 'https://i0.wp.com/www.keepingkidsconnected.com/wp-content/uploads/2017/06/unicorn-e1498877455455.jpg?fit=300%2C294&ssl=1'
        }

        rawjson[key].push(link1);
        rawjson[key].push(link2);
    }
    
    res.send(JSON.stringify(rawjson))

});

module.exports = router;
