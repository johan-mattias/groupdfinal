var express = require('express');
var idgen = require('idgen');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');
var multer  = require('multer');
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'public/images/uploaded_images/')
    },
    filename: function (req, file, cb) {
      cb(null, idgen(16));
    }
  })
var upload = multer({ storage: storage });


router.post('/', upload.single('image'), function(req, res){

    var post  = req.body;
    var beer = post.beer;
    var user = post.user;

    if  ((user == null || user<1) && (beer == null || beer<1) && (!req.file))
    return res.status(400).send('Image were not uploaded. Invalid inputs.');


    var file = req.file;
    var img_name = "test";
    var id = file.filename;

    file.filename = id.toString();

    console.log(file)

    console.log(id)
	res.send("id: "+id.toString());
    imageInsertDB(id,1,1);
	});

    function imageInsertDB(image,user,beer){
        var images = "insert into images (link,userID,beerID) "+
            "values (?,?,?)";
        var inserts = [image,user,beer];
            db.query(images,inserts, function (err, result) {
                console.log("Image with id: " +image.toString()+ " uploaded.");
              });

    }

module.exports = router;