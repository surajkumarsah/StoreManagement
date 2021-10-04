
// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
var firebaseConfig = {
    apiKey: "AIzaSyBq6oCdHA8cD8K0FEKXj1ZY-wtmxvU204s",
    authDomain: "happyworld-7b5bc.firebaseapp.com",
    databaseURL: "https://happyworld-7b5bc-default-rtdb.firebaseio.com",
    projectId: "happyworld-7b5bc",
    storageBucket: "happyworld-7b5bc.appspot.com",
    messagingSenderId: "223090719207",
    appId: "1:223090719207:web:ac7e7ca41be03c04fd2542",
    measurementId: "G-3EJBN209SV"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
// firebase.analytics();


// Firebase Database Reference and the child
const dbRef = firebase.database().ref();
const usersRef = dbRef.child('User');

//Curd Operation


const addUserBtnUI = document.getElementById("add-user-btn");
addUserBtnUI.addEventListener("click", addUserBtnClicked)

function addUserBtnClicked() {

    const usersRef = dbRef.child('User');

    const addUserInputsUI = document.getElementsByClassName("user-input");

    // this object will hold the new user information
    let newUser = {};

    // loop through View to get the data for the model
    for (let i = 0, len = addUserInputsUI.length; i < len; i++) {

        let key = addUserInputsUI[i].getAttribute('data-key');
        let value = addUserInputsUI[i].value;
        newUser[key] = value;
    }

    usersRef.push(newUser);

    alert("User Added Successfully."+newUser.name + " " + newUser.age +" "+ newUser.email);





}

