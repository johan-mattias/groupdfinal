var mysql = require("mysql");


var express = require('express');

var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

// Routes
var index = require('./routes/index');
var users = require('./routes/users');
var test = require('./routes/test');
var stream = require('./routes/stream');
var db = require('./db');
var beer = require('./routes/beer');
var image = require('./routes/image');

//SSL cert and https
var fs = require('fs');
var http = require('http');
var https = require('https');
var privateKey  = fs.readFileSync('sslcert/beerdex.key', 'utf8');
var certificate = fs.readFileSync('sslcert/beerdex.crt', 'utf8');

var credentials = {key: privateKey, cert: certificate};



var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use('/getImage', express.static(__dirname + '/public/images/uploaded_images'));

app.use('/', index);
app.use('/users', users);
app.use('/test', test);
app.use('/stream', stream);
app.use('/beer', beer);
app.use('/image', image);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});



// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});




var httpServer = http.createServer(app);
var httpsServer = https.createServer(credentials, app);

httpServer.listen(8080);
httpsServer.listen(8443);


module.exports = app;
