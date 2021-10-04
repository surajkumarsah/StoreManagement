$(document).ready(function(){

    // jQuery methods go here...

});

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


var messagesRef = firebase.database().ref('Temple');

$('#submit').on('click', function(e){
    e.preventDefault();
    validation()

    // var desc = ClassicEditor.instances['tempDesc'].getData();
    // alert('hello')
})

// document.getElementById(
//     'contactForm').addEventListener('submit', submitForm);


function validation(){
    let tempName = $('#tempName').val();
    let tempDesc = CKEDITOR.instances['tempDesc'].getData();
    let file = $('#files').val();

    if (tempName === ""){
        alert('Temple Name is empty.');
    }else if (tempDesc === ""){
        alert('Temple Description is empty.');
    }else if (file === ""){
        alert('File is empty.');
    }else{
        uploadimage();
    }
}


//uploading file in storage
function uploadimage(){
    const d = new Date();
    let timestamp = d.getDate()+''+d.getTime();
    var type = getInputVal('tempName');
    var storage = firebase.storage();
    var file=document.getElementById("files").files[0];
    var storageref=storage.ref();
    var thisref=storageref.child("Temple").child(timestamp).child(file.name).put(file);
    thisref.on('state_changed',function(snapshot) {


    }, function(error) {
        alert('Error: '+error)
    }, function() {
        // Uploaded completed successfully, now we can get the download URL
        thisref.snapshot.ref.getDownloadURL().then(function(downloadURL) {
            //getting url of image
            document.getElementById("url").value=downloadURL;
            alert('uploaded successfully');
            saveMessage(downloadURL);
        });
    });

    // Get values
    var url = getInputVal('url');
    // Save message
    // saveMessage(url);
}
function getInputVal(id){
    document.getElementById('contactForm').reset();

}

// Function to get get form values
function getInputVal(id){
    return document.getElementById(id).value;
}

// Save message to firebase database
function saveMessage(url){
    let tempName = $('#tempName').val();
    let tempDesc = CKEDITOR.instances['tempDesc'].getData();
    var newMessageRef = messagesRef.push();
    newMessageRef.set({
        tempImage:url,
        tempDesc: tempDesc,
        tempName: tempName
    });
}
