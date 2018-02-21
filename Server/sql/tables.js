var mysql = require('mysql');
var db = require('../db');

var users = "Create table users (\
    id int auto_increment primary key,\
    Name string\
)"

var countries ="Create table countries(\
    id int auto_increment primary key,\
    Name string\
)"

var beerTypes ="Create table beerTypes(\
    id int auto_increment primary key,\
    Name string\
)"

var breweries = "Create table breweries (\
    id  int auto_increment primary key,\
    Name string\
)"

var beers = "Create table beers (\
    id  int auto_increment primary key,\
    Name string,\
    FOREIGN KEY (beertype) REFERENCES beerTypes(id),\
    FOREIGN KEY (brewery) REFERENCES breweries(id),\
    FOREIGN KEY (country) REFERENCES countries(id)\
)"

var images = "Create table images (\
id int auto_increment primary key,\
Link string,\
FOREIGN KEY (user) REFERENCES users(id),\
FOREIGN KEY (beer) REFERENCES beers(id)\
)"

var comments = "Create table comments (\
id int auto_increment primary key,\
FOREIGN KEY (user) REFERENCES users(id),\
FOREIGN KEY (image) REFERENCES images(id)\
)"

db.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
    con.query(users, function (err, result) {
      if (err) throw err;
      console.log("Table created");
    });
    con.query(countries, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    con.query(beerTypes, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    con.query(breweries, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    con.query(beers, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    con.query(images, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    con.query(comments, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
  });