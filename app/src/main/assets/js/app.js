

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


    readUserData();


    // --------------------------
    // READ
    // --------------------------
    function readUserData() {

        const userListUI = document.getElementById("user-list");

        usersRef.on("value", snap => {

            userListUI.innerHTML = ""

            snap.forEach(childSnap => {

                let key = childSnap.key,
                    value = childSnap.val()

                let $li = document.createElement("li");

                // edit icon
                let editIconUI = document.createElement("span");
                editIconUI.class = "edit-user";
                editIconUI.innerHTML = " ✎";
                editIconUI.setAttribute("userid", key);
                editIconUI.addEventListener("click", editButtonClicked)

                // delete icon
                let deleteIconUI = document.createElement("span");
                deleteIconUI.class = "delete-user";
                deleteIconUI.innerHTML = " ☓";
                deleteIconUI.setAttribute("userid", key);
                deleteIconUI.addEventListener("click", deleteButtonClicked)

                $li.innerHTML = value.name;
                $li.append(editIconUI);
                $li.append(deleteIconUI);

                $li.setAttribute("user-key", key);
                $li.addEventListener("click", userClicked)
                userListUI.append($li);

            });


        })

    }



    function userClicked(e) {

        var userID = e.target.getAttribute("user-key");

        const userRef = dbRef.child('User/' + userID);
        const userDetailUI = document.getElementById("user-detail");

        userDetailUI.innerHTML = ""

        userRef.on("child_added", snap => {


            snap.forEach(childSnap => {
                var $p = document.createElement("p");
                $p.innerHTML = childSnap.key  + " - " +  childSnap.val();
                userDetailUI.append($p);
            })

        });

    }





    // --------------------------
    // DELETE
    // --------------------------
    function deleteButtonClicked(e) {

        e.stopPropagation();

        var userID = e.target.getAttribute("userid");

        const userRef = dbRef.child('User/' + userID);

        userRef.remove();

    }


    // --------------------------
    // EDIT
    // --------------------------
    function editButtonClicked(e) {

        document.getElementById('edit-user-module').style.display = "block";

        //set user id to the hidden input field
        document.querySelector(".edit-userid").value = e.target.getAttribute("userid");

        const userRef = dbRef.child('User/' + e.target.getAttribute("userid"));

        // set data to the user field
        const editUserInputsUI = document.querySelectorAll(".edit-user-input");


        userRef.on("value", snap => {

            for(var i = 0, len = editUserInputsUI.length; i < len; i++) {

                var key = editUserInputsUI[i].getAttribute("data-key");
                editUserInputsUI[i].value = snap.val()[key];
            }

        });




        const saveBtn = document.querySelector("#edit-user-btn");
        saveBtn.addEventListener("click", saveUserBtnClicked)
    }


    function saveUserBtnClicked(e) {

        const userID = document.querySelector(".edit-userid").value;
        const userRef = dbRef.child('User/' + userID);

        var editedUserObject = {}

        const editUserInputsUI = document.querySelectorAll(".edit-user-input");

        editUserInputsUI.forEach(function(textField) {
            let key = textField.getAttribute("data-key");
            let value = textField.value;
            editedUserObject[textField.getAttribute("data-key")] = textField.value
        });



        userRef.update(editedUserObject);

        document.getElementById('edit-user-module').style.display = "none";


    }











