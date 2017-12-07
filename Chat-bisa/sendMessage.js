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



var registrationToken = "fzpW2ST_lYQ:APA91bH2_0FgQ9MoHBH4t92mmP1SVNrlx6dY0Ti3FQJQHXgHmCNymPJz-m5dJX_JOfOgf6m7_dFPH5zp3BjQefmzCMbuC4uJUK_DDr2A5k5k-glrnVmfUp_VpYIEFVrkBGUo-fboZF45";

// See the "Defining the message payload" section below for details
// on how to define a message payload.
var payload = {
  data: {
    score: "850",
    time: "2:44"
  }
};

send.sendMessage(registrationToken,payload);
