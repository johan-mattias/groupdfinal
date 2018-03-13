var express = require('express');
var idgen = require('idgen');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');
var multer  = require('multer');
const mime = require('mime');
var thumb = require('node-thumbnail').thumb;
var imagePath = 'public/images/uploaded_images/';
resolve = require('path').resolve;

/****************************************************/
/*          CODE TO SHOW SERVER #1                  */
/****************************************************/

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
    
    cb(null, imagePath)
    },
    filename: function (req, file, cb) {
    
    //   cb(null, idgen(16)+'.'+mime.extension(file.mimetype));
    cb(null, idgen(16)+'.jpeg');
    }
})
var upload = multer({ storage: storage });


router.post('/upload', upload.single('image'), function(req, res){

    var post  = req.body;
    var beerID = post.beerID;
    var userID = post.userID;
    var description = post.description;
    
    


    if  ((userID == null) || (beerID == null) || (!req.file))
    return res.status(400).send('Image were not uploaded. Invalid inputs.');

    if (description===null) {
        description=""
    }

    var file = req.file;
    var filename = file.filename;


    thumb({
        source: __basedir +'/'+imagePath+filename, // could be a filename: dest/path/image.jpg
        destination: __basedir +'/'+imagePath,
        width:400,
        quiet:true,
      }, function(files, err, stdout, stderr) {
        if(err){
            console.log("Something went wrong with thumb!");
            console.log(err);
            res.status(400).send("Image wasnt uploaded, problem when creating thumbnail");
            return
        }else{
        }
      });



    imageInsertDB(filename,userID,beerID,description);
	res.status(200).send("image uploaded");

	});

router.post('/download', function(req, res){

    var post  = req.body;
    imageID = post.imageID
    getImageDB(imageID,function(result){
        if(result){
        imageName = result.link;
        var path = resolve(imagePath+imageName);
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
            console.log("Error! Image with id: " +imageID+ " wasnt found.");
        }else
            console.log("Image with id: " +imageID+ " found.");
            var res = result[0];
            console.log("query: ",res);
            return callback(res);
        });
}

function imageInsertDB(image,user,beer,description){
    var images = "insert into images (link,userID,beerID,description) "+
        "values (?,?,?,?)";
    var inserts = [image,user,beer,description];
        db.query(images,inserts, function (err, result) {
            if (err){
                console.log("Image with id: " +image.toString()+ " not uploaded.");
            }else{
                console.log("Image with id: " +image.toString()+ " uploaded.");
            
            }
            });

}

module.exports = router;