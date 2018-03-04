var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');

/* GET users listing. */
router.post('/', function(req, res, next) {

    post = req.body;
    lastImageID = post.lastImageID;
    getMultipleImageDB(lastImageID,20,function(result){
        if(result){
        res.send(result);
    }else{
        res.status(400).send("No images was found")
    }
    })

});

function getMultipleImageDB(imageID,limit,callback){
    var query = "select * from stream where image_id>? limit ?";
    var inserts = [imageID,limit];
    db.query(query,inserts, function (err, result) {
        if(err){
            console.log("Error! Image with id: " +imageID+ " wasnt found.");
        }else
            console.log("Image with id: " +imageID+ "+ up to" +limit +"was sent");
            var res = result;
            return callback(res);
        });
}

module.exports = router;
