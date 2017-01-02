var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var parser = require('body-parser').urlencoded({extended: true});

app.use(express.static('public'));
app.set('view engine', 'ejs');
app.set('views', './views');

// socket io
require('./api/socket_io.js')(io);

app.get('/', (req, res)=>{
  res.render('home');
});

// api
app.get('/api/update',require('./api/update.js'));
app.get('/api/socket', require('./api/request.js'));

var port = process.env.PORT || 3000;
server.listen(port, ()=>{
  console.log("server started!!");
});
