var mysql = require('mysql');
var db = require('../db');

var stream = "drop view stream"

console.log("Connected!");
db.query(stream, function (err, result) {
  if (err) console.log("Table commenTs not dropped");
  else console.log("Table comments dropped");
  process.exit();
});