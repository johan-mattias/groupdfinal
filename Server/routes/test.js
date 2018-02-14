var express = require('express');
var router = express.Router();
var mysql = require('mysql');

router.get('/', function(req, res, next) {

    var rawjson = {};
    var key = 'startbeerlist';
    rawjson[key] = [];
    

    var beer = {
        id: '1',
        name: 'henieken',
        type: 'lager'
    }


    var beer2 = {
        id: '2',
        name: 'carlsberg',
        type: 'lager'
    }
    rawjson[key].push(beer);
    rawjson[key].push(beer2);
    
    res.send(JSON.stringify(rawjson))
});

module.exports = router;