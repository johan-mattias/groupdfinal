var mysql = require('mysql');
var db = require('../db');


var users = "insert into users (name) values ('Edvin');"
var countries = "insert into countries (name) values ('Netherlands');"
var beerTypes = "insert into beerTypes (name) values ('Lager');"
var breweries = "insert into breweries (name) values ('Heineken');"
var beers = "insert into beers (name, beertypeID, breweryID, countryID) "+
            "values ('Heineken',1,1,1)"
var images = "insert into images (link,userID,beerID) "+
            "values ('https://res.cloudinary.com/teepublic/image/private/s--UvSUNgzW--/b_rgb:fffffe,t_Heather%20Preview/c_lpad,f_jpg,h_630,q_90,w_1200/v1494469473/production/designs/1595608_1.jpg',"+
            "1,1)"


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

  db.query(beers, function (err, result) {
    if (err) throw error;
    else console.log("Table beers filled with dummies");
  });

  db.query(images, function (err, result) {
    if (err) throw error;
    else console.log("Table images filled with dummies");
  });


