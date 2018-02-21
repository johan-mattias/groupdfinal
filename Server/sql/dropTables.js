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
  if (err) throw err;
  console.log("Table dropped");
});
db.query(images, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });
db.query(beers, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });
db.query(breweries, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });
db.query(beerTypes, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });
db.query(countries, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });
db.query(users, function (err, result) {
    if (err) throw err;
    console.log("Table dropped");
  });