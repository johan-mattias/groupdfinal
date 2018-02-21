var mysql = require('mysql');
var db = require('../db');

var users = "Create table users (" +
    "id int auto_increment primary key," +
    "Name varchar(100)" +
")"

var countries ="Create table countries("+
    "id int auto_increment primary key,"+
    "Name varchar(100)"+
")"

var beerTypes ="Create table beerTypes("+
    "id int auto_increment primary key,"+
   "Name varchar(100)"+
")"

var breweries = "Create table breweries ("+
    "id  int auto_increment primary key,"+
    "Name varchar(100)"+
")"

var beers = "Create table beers ("+
    "id  int auto_increment primary key,"+
    "Name varchar(100),"+
    "beertypeID int,"+
    "breweryID int,"+
    "countryID int,"+
    "FOREIGN KEY (beertype) REFERENCES beerTypes(id),"+
    "FOREIGN KEY (brewery) REFERENCES breweries(id),"+
    "FOREIGN KEY (country) REFERENCES countries(id)"+
")"

var images = "Create table images ("+
    "id int auto_increment primary key,"+
    "link varchar(100),"+
    "userID int,"+
    "beerID int,"+
    "FOREIGN KEY (userID) REFERENCES users(id),"+
    "FOREIGN KEY (beerID) REFERENCES beers(id)"+
")"

var comments = "Create table comments ("+
    "id int auto_increment primary key,"+
    "userID int,"+
    "imageID int,"+
    "FOREIGN KEY (user) REFERENCES users(id),"+
    "FOREIGN KEY (image) REFERENCES images(id),"+
    "comment varchar(400)"+
")"


    console.log("Connected!");
    db.query(users, function (err, result) {
      if (err) throw err;
      console.log("Table created");
    });
    db.query(countries, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    db.query(beerTypes, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    db.query(breweries, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    db.query(beers, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    db.query(images, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });
    db.query(comments, function (err, result) {
        if (err) throw err;
        console.log("Table created");
      });