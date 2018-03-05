var mysql = require('mysql');
var db = require('../db');

var users = "Create table users (" +
    "id int auto_increment primary key," +
    "Name varchar(100) not null" +
    ")"

var countries ="Create table countries("+
    "id int auto_increment primary key,"+
    "Name varchar(100),"+
    "Unique (Name)"+
    ")"

var beerTypes ="Create table beerTypes("+
    "id int auto_increment primary key,"+
    "Name varchar(100),"+
    "Unique (Name)"+
    ")"

var breweries = "Create table breweries ("+
    "id  int auto_increment primary key,"+
    "Name varchar(100),"+
    "Unique (Name)"+
    ")"

var beers = "Create table beers ("+
    "id  int auto_increment primary key,"+
    "Name varchar(100) not null,"+
    "beerTypeID int not null,"+
    "breweryID int not null,"+
    "countryID int not null,"+
    "FOREIGN KEY (beertypeID) REFERENCES beerTypes(id),"+
    "FOREIGN KEY (breweryID) REFERENCES breweries(id),"+
    "FOREIGN KEY (countryID) REFERENCES countries(id),"+
    "Unique (Name,beerTypeID,BreweryID,CountryID)"+
    ")"

var images = "Create table images ("+
    "id int auto_increment primary key,"+
    "link varchar(50),"+
    "userID int not null,"+
    "beerID int not null,"+
    "description varchar(1000),"+
    "FOREIGN KEY (userID) REFERENCES users(id),"+
    "FOREIGN KEY (beerID) REFERENCES beers(id),"+
    "Unique (link)"+
    ")"

var comments = "Create table comments ("+
    "id int auto_increment,"+
    "userID int not null,"+
    "imageID int not null,"+
    "FOREIGN KEY (userID) REFERENCES users(id),"+
    "FOREIGN KEY (imageID) REFERENCES images(id),"+
    "comment varchar(400), "+
    "primary key (imageID,id)"+
") ENGINE=MyISAM;"


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
        process.exit();
      });