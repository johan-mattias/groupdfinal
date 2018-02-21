var mysql = require('mysql');
var db = require('../db');

var users = "drop table users"
var countries = "drop table countries"
var beerTypes = "drop table beerTypes"
var breweries = "drop table breweries"
var beers = "drop table beers"
var images = "drop table images"
var comments = "drop table comments"




console.log("Connected!");
db.query(comments, function (err, result) {
  if (err) console.log("Table commenTs not dropped");
  else console.log("Table comments dropped");
});
db.query(images, function (err, result) {
    if (err) console.log("Table images not dropped");
    else console.log("Table images dropped");
  });
db.query(beers, function (err, result) {
    if (err) console.log("Table beers not dropped");
    else console.log("Table beers dropped");
  });
db.query(breweries, function (err, result) {
    if (err) console.log("Table breweries not dropped");
    else console.log("Table breweries dropped");
  });
db.query(beerTypes, function (err, result) {
    if (err) console.log("Table beerTypes not dropped");
    else console.log("Table beerTypes dropped");
  });
db.query(countries, function (err, result) {
    if (err) console.log("Table countries not dropped");
    else console.log("Table countries dropped");
  });
db.query(users, function (err, result) {
    if (err) console.log("Table users not dropped");
    else console.log("Table users dropped");
    process.exit();
  });
