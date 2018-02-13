var mysql = require('mysql');
//Database connection
var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'beeradmin',
    password : 'GemenbEer0215',
    database : 'beerdex'
  });
  
  connection.connect(function(err) {
    if (err) {
      console.error('error connecting: ' + err.stack);
      return;
    }
  
    console.log('connected as id ' + connection.threadId);
  });

  module.exports = connection;