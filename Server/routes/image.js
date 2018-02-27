var express = require('express');
var idgen = require('idgen');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');
var multer  = require('multer');
const mime = require('mime');
resolve = require('path').resolve;

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'public/images/uploaded_images/')
    },
    filename: function (req, file, cb) {
      cb(null, idgen(16)+'.'+mime.extension(file.mimetype));
    }
  })
var upload = multer({ storage: storage });


router.post('/upload', upload.single('image'), function(req, res){

    var post  = req.body;
    var beer = post.beer;
    var user = post.user;

    if  ((user == null || user<1) && (beer == null || beer<1) && (!req.file))
    return res.status(400).send('Image were not uploaded. Invalid inputs.');


    var file = req.file;
    var id = file.filename;

	res.send("id: "+id.toString());
    imageInsertDB(id,1,1);
	});

router.post('/download', function(req, res){

    var post  = req.body;
    imageID = post.imageID
    getImageDB(imageID,function(result){
        if(result){
        imageName = result.link;
        var path = resolve('public/images/uploaded_images/'+imageName);
        res.sendFile(path);
    }else{
        res.status(400).send("Image was not found")
    }
    });
    
    });


function getImageDB(imageID,callback){
    var query = "select * from images where id=?";
    var inserts = [imageID];
    db.query(query,inserts, function (err, result) {
        if(err){
            console.log("Error! Image with id: " +imageID.toString()+ " wasnt found.");
        }else
            console.log("Image with id: " +imageID.toString()+ " found.");
            var res = result[0];
            console.log("query: ",res);
            return callback(res);
        });
}

function imageInsertDB(image,user,beer){
    var images = "insert into images (link,userID,beerID) "+
        "values (?,?,?)";
    var inserts = [image,user,beer];
        db.query(images,inserts, function (err, result) {
            console.log("Image with id: " +image.toString()+ " uploaded.");
            });

}

module.exports = router;