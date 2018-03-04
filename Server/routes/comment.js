var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');
const mime = require('mime');



router.post('/upload', function(req, res){

    var post  = req.body;
    var image = post.image;
    var user = post.user;
    var comment = post.comment;
    

    if  ((user === null) && (image === null) && (comment === null))
    return res.status(400).send('Comment were not uploaded. Invalid inputs.');

    commentInsertDB(user,image,comment);
	res.status(200).send("Comment uploaded");

	});

router.post('/download', function(req, res){

    var post  = req.body;
    imageID = post.imageID
    lastCommentID = post.lastCommentID;
    getCommentDB(imageID,lastCommentID,function(result){
        if(result){
        
        res.status(200).send(result);
    }else{
        res.status(400).send("Image was not found")
    }
    });
    
    });


function getCommentDB(imageID,lastCommentID, callback){
    var query = "select * from comments where imageid=? and id>? limit 20";
    var inserts = [imageID,lastCommentID];
    db.query(query,inserts, function (err, result) {
        if(err){
            console.log("Error! Comment on image: "+ imageID +" with id: " +lastCommentID+ " wasnt found.");
        }else
            console.log("Comment on image: "+ imageID +" with id: " +lastCommentID+ " was found.");
            var res = result;
            return callback(res);
        });
}

function commentInsertDB(user,image,comment){
    
    var comments = "insert into comments (userID,imageID,comment) "+
        "values (?,?,?)";
    var inserts = [user,image,comment];
        db.query(comments,inserts, function (err, result) {
            if (err){
                console.log(err)
                console.log("Comment not uploaded.");
            }else{
                console.log("Comment uploaded.");
            
            }
            });

}

module.exports = router;