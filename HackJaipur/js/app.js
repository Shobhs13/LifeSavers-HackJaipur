(function(){
    var firebaseConfig = {
     apiKey: "AIzaSyB3nDviElVGYksOkRl0BCUw2C386eGsraw",
     authDomain: "lifesaver-b170d.firebaseapp.com",
     databaseURL: "https://lifesaver-b170d.firebaseio.com",
     projectId: "lifesaver-b170d",
     storageBucket: "lifesaver-b170d.appspot.com",
     messagingSenderId: "293632693174",
     appId: "1:293632693174:web:045dffb5f431659fb4e717",
     measurementId: "G-2CR67178G2"
     };
  // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();
 
    const preObject = document.getElementById('object');
 
    const dbRefObject = firebase.database.ref().child('object');
 
    dbRefObject.on('value', snap => console.log(snap.value()));
 
 }());
 
 