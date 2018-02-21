var mysql = require('mysql');
var db = require('../db');


var users = "insert into users (name) values ('Edvin');"
var countries = "insert into countries (name) values ('Netherlands');"
var beerTypes = "insert into beerTypes (name) values ('Lager');"
var breweries = "insert into breweries (name) values ('Heineken');"
var beers = "insert into beers"+
            "set name = 'Heineken123123123',"+
            "beertypeID = (select beerTypes.id from beerTypes where id=1),"+
            "breweryID = (select breweries.id from breweries where id=1),"+
            "countryID = (select countries.id from countries where id=1)"



console.log("Connected!");
db.query(users, function (err, result) {
  if (err) throw error;
  else console.log("Table user filled with dummies");
});

db.query(countries, function (err, result) {
    if (err) throw error;
    else console.log("Table countries filled with dummies");
  });

db.query(beerTypes, function (err, result) {
    if (err) throw error;
    else console.log("Table beerTypes filled with dummies");
  });

db.query(breweries, function (err, result) {
    if (err) {
        console.log(result);
        throw error;}
    else console.log("Table breweries filled with dummies");
  });

//   db.query(beers, function (err, result) {
//     if (err) throw error;
//     else console.log("Table beers filled with dummies");
//   });


