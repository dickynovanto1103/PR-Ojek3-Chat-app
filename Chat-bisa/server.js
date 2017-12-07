var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var send = require('./app');
var firebase = require("firebase");
  var config = {
    apiKey: "AIzaSyDMp69DxSxZsGVwfipmzE7DwhG-zmusGis",
    authDomain: "tugas-besar-3.firebaseapp.com",
    databaseURL: "https://tugas-besar-3.firebaseio.com",
    projectId: "tugas-besar-3",
    storageBucket: "tugas-besar-3.appspot.com",
    messagingSenderId: "75908537450"
  };
  firebase.initializeApp(config);

app.get('/', function(req,res) {
	console.log("Got a GET requiest for the homepage");
	res.send('Hello GET');
})


app.get('/formGet.html', function(req,res) {
	res.sendFile(__dirname + "/" + "formGet.html");
})
app.get('/formPost.html', function(req,res) {
	res.sendFile(__dirname + "/" + "formPost.html");
})

//create application/w--www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({extended: false});

app.post('/process_post', urlencodedParser, function(req,res) {
	//prepare output in json format
	var registrationToken = "fzpW2ST_lYQ:APA91bH2_0FgQ9MoHBH4t92mmP1SVNrlx6dY0Ti3FQJQHXgHmCNymPJz-m5dJX_JOfOgf6m7_dFPH5zp3BjQefmzCMbuC4uJUK_DDr2A5k5k-glrnVmfUp_VpYIEFVrkBGUo-fboZF45";
	var payload = {
		data: {
			first_name:req.body.first_name,
			last_name:req.body.last_name	
		}
	};
	console.log(payload);
	res.sendFile(__dirname + "/" + "formPost.html");
	send.sendMessage(registrationToken,payload);
	//res.end(JSON.stringify(response));
	
})


var server = app.listen(8080,function() {
	var host = server.address().address;
	var port = server.address().port;

	console.log("Example app listening at http://%s:%s", host, port);

})