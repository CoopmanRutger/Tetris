"use strict";

/* global EventBus */
//let eb = new EventBus("http://localhost:8021/tetris-21/socket");
let eb = new EventBus("http://172.21.22.52:48200/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

let loginAllowed = false;

function init() {
    ResetPlayerInSession();
    eb.onopen = function () {
        console.log("opened");
    };
    document.querySelector("#Login").addEventListener("click", checkValues);
}

function ResetPlayerInSession() {
    sessionStorage.clear();
}

function checkValues(e) {
    e.preventDefault();
    let username = document.querySelector("#username").value;
    let password = document.querySelector("#password").value;

    if (username === "" || password === "") {
        document.querySelector("#errorMessage").style.display = "block";
        document.querySelector("#errorMessage").innerHTML = "You need to fill in username and password";
        return;
    }

    index(username, password);
}

function mayPersonLogin(username) {
    if (loginAllowed === "true") {
        let loginValues = {username: username, login: loginAllowed};
        sessionStorage.setItem("index", JSON.stringify(loginValues));
        window.location.href = "homescreen.html";
    } else {
        document.querySelector("#errorMessage").style.display = "block";
        document.querySelector("#errorMessage").innerHTML = "Cannot sign in, username or password is wrong.";
    }
}

function index(username, password) {
    let loginValues = {username: username, password: password};
    console.log(JSON.stringify(loginValues));
    eb.send("tetris-21.socket.login", JSON.stringify(loginValues), function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });

    eb.registerHandler("tetris-21.socket.login.server", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body);
        loginAllowed = message.body;
        mayPersonLogin(username);
    })
}