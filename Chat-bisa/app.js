  // Initialize Firebase
var admin = require("firebase-admin");

var serviceAccount = require("./tugas-besar-3-firebase-adminsdk-ledqf-9af42105a9.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://tugas-besar-3.firebaseio.com"
});


// This registration token comes from the client FCM SDKs.
exports.sendMessage = function(registrationToken, payload) {
  // Send a message to the device corresponding to the provided
  // registration token.
 // This registration token comes from the client FCM SDKs.

// Send a message to the device corresponding to the provided
// registration token.
  admin.messaging().sendToDevice(registrationToken, payload)
  .then(function(response) {
    // See the MessagingDevicesResponse reference documentation for
    // the contents of response.
    console.log("Successfully sent message:", response);
  })
  .catch(function(error) {
    console.log("Error sending message:", error);
  });
};



