var mysql = require('mysql');
var db = require('../db');


var users = "insert into users (name) values ('Edvin');"
var countries = "insert into countries (name) values ('Netherlands');"
var countries2 = "insert into countries (name) values ('Denmark');"
var beerTypes = "insert into beerTypes (name) values ('Lager');"
var breweries = "insert into breweries (name) values ('Heineken');"
var breweries2 = "insert into breweries (name) values ('Carlsberg');"
var beers = "insert into beers (name, beertypeID, breweryID, countryID) "+
            "values ('Heineken',1,1,1)"
var beers2 = "insert into beers (name, beertypeID, breweryID, countryID) "+
            "values ('Backyard Brew Bumble Bee 17',1,2,2)"
var beers3 = "insert into beers (name, beertypeID, breweryID, countryID) "+
            "values ('Carlsberg Export',1,2,2)"
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

  db.query(countries2, function (err, result) {
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

db.query(breweries2, function (err, result) {
  if (err) {
      console.log(result);
      throw error;}
  else console.log("Table breweries filled with dummies");
});

db.query(beers, function (err, result) {
  if (err) throw error;
  else console.log("Table beers filled with dummies");
});

db.query(beers2, function (err, result) {
  if (err) throw error;
  else console.log("Table beers filled with dummies");
});

db.query(beers3, function (err, result) {
  if (err) throw error;
  else console.log("Table beers filled with dummies");
  process.exit();
}); 




