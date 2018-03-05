var mysql = require('mysql');
var db = require('../db');

var stream = "drop view stream"
var comment = "drop view comment_view"

console.log("Connected!");
db.query(stream, function (err, result) {
  if (err) console.log("Table stream not dropped");
  else console.log("Table stream dropped");
});

db.query(comment, function (err, result) {
  if (err) console.log("Table commenTs not dropped");
  else console.log("Table comments dropped");
  process.exit();
});