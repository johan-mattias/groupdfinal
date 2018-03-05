var mysql = require('mysql');
var db = require('../db');
var comment = "insert into comments (userID,imageID,comment) "+
"values (1,2,'ajsdjkasd')";


db.query(comment, function (err, result) {
    if (err) throw error;
    process.exit();
});

