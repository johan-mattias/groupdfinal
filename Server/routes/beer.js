var express = require('express');
var router = express.Router();
var mysql = require('mysql');
var db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {
	db.query('SELECT * from beers', function (error, results, fields) {
		if (error) throw error;
		res.send(results);
	});
});

module.exports = router;