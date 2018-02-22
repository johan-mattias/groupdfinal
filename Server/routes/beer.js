var router = express.Router();
var mysql = require('mysql');
var db = require('../db');

/* GET users listing. */
router.get('/', function(req, res, next) {
    var beername = req.body.beername
	db.query('SELECT ? from beers', beername, function (error, results, fields) {
		if (error) throw error;
		res.send(JSON.stringify({"status": 200, "error": null, "response": results}));
	});
});