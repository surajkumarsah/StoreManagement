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
const templeRef = dbRef.child('Temple');


readImageData();


// --------------------------
// READ
// --------------------------


// <script>
    // --------------------------
    // READ
    // --------------------------
    function readImageData() {

        const ImageListUI = document.getElementById("portfolio_wrapper");

        templeRef.on("value", snap => {

            ImageListUI.innerHTML = ""


            snap.forEach(childSnap => {

                let key = childSnap.key,
                    value = childSnap.val()

                // let image = document.getElementById("temp_image");
                // image.setAttribute("src", value.tempImage);
                //
                // document.getElementById("temp_desc").innerHTML = value.tempImage;
                // document.getElementById("temp_name").innerHTML = value.tempName;

                let figStyle = "position: absolute; left: 0px; top: 0px; transform: translate3d(0px, 0px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;";

                let $figureDiv = document.createElement("figure");
                $figureDiv.setAttribute("style", figStyle);
                $figureDiv.setAttribute("class", "portfolio-item one-four");
                $figureDiv.setAttribute("id", key);

                let figDiv = document.createElement("div");
                figDiv.setAttribute("class", "portfolio_img");

                let temp_image = document.createElement("img");
                temp_image.setAttribute("src", value.tempImage);
                temp_image.setAttribute("id", "temp_image");
                temp_image.setAttribute("alt", "Portfolio ");


                let figCaption = document.createElement("figcaption");
                let figCapDiv = document.createElement("div");

                let anch = document.createElement("a");
                anch.setAttribute("class", "fancybox");
                anch.href = value.tempImage;

                let h2 = document.createElement("h2");
                h2.innerHTML = value.tempName;

                let p = document.createElement("p");
                p.innerHTML = value.tempDesc;


                $figureDiv.append(figDiv);
                figDiv.append(temp_image);

                $figureDiv.append(figCaption);
                figCaption.append(figCapDiv);
                figCapDiv.append(anch);
                anch.append(h2);
                anch.append(p);


                ImageListUI.append($figureDiv);

            });

        });

    }
// </script>


















//
//
//
// function readImageData() {
//
//     const userListUI = document.getElementById("user-list");
//
//     templeRef.on("value", snap => {
//
//         userListUI.innerHTML = ""
//
//         snap.forEach(childSnap => {
//
//             let key = childSnap.key,
//                 value = childSnap.val()
//
//             let image = document.getElementById("temp_image");
//             image.setAttribute("src", value.tempImage);
//
//             document.getElementById("temp_desc").innerHTML = value.tempImage;
//             document.getElementById("temp_name").innerHTML = value.tempName;
//
//
//             // let $li = document.createElement("li");
//             //
//             // // edit icon
//             // let editIconUI = document.createElement("span");
//             // editIconUI.class = "edit-user";
//             // editIconUI.innerHTML = " ✎";
//             // editIconUI.setAttribute("userid", key);
//             // editIconUI.addEventListener("click", editButtonClicked)
//             //
//             // // delete icon
//             // let deleteIconUI = document.createElement("span");
//             // deleteIconUI.class = "delete-user";
//             // deleteIconUI.innerHTML = " ☓";
//             // deleteIconUI.setAttribute("userid", key);
//             // deleteIconUI.addEventListener("click", deleteButtonClicked)
//             //
//             // $li.innerHTML = value.name;
//             // $li.append(editIconUI);
//             // $li.append(deleteIconUI);
//             //
//             // $li.setAttribute("user-key", key);
//             // $li.addEventListener("click", userClicked)
//             // userListUI.append($li);
//
//         });
//
//
//     })

// }
