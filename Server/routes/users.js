var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {

	db.query('SELECT * from users', function (error, results, fields) {
		if (error) throw error;
		res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
	});
});


router.post('/',function(req, res){
	var name = req.body.name; 
	console.log(name)
	db.query('insert into values (?)', name.toString(),function(err, result){
		if(err) throw err;
		console.log("1 record inserted");
	});
	res.send(name+" has been added to database");

	});

module.exports = router;
