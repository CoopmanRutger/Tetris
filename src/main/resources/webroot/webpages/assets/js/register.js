"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");
// let eb = new EventBus("http://172.31.27.98:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

let loginMade = false;
let usernameExists = false;

function init() {
    eb.onopen = function() {
        console.log("opened");
    };
    document.querySelector("#register").addEventListener("click", checkValues);
    document.querySelector("#registerUsername").addEventListener("focusout", checkUsername);
}

function checkValues(e) {
    e.preventDefault();
    let email = document.querySelector("#registerEmail").value;
    let username = document.querySelector("#registerUsername").value;
    let playername = document.querySelector("#registerPlayername").value;
    let password = document.querySelector("#registerPassword").value;
    let repeatPassword = document.querySelector("#repeatPassword").value;
    let errorMessageElement = document.querySelector("#errorMessage");

    if (email === "" || username === "" || password === "" || repeatPassword === "" || playername === "") {
        errorMessageElement.style.display = "block";
        errorMessageElement.innerHTML = "Not all fields are filled out, please fill in all fields.";
        return;
    } else if (usernameExists === true) {
        errorMessageElement.style.display = "block";
        errorMessageElement.innerHTML = "Username does already exist, try other username.";
        return;
    }

    if (passwordIsTheSame(password, repeatPassword)) {
        register(email, username, password, playername);
    } else {
        errorMessageElement.style.display = "block";
        errorMessageElement.innerHTML = "The passwords are not the same.";
    }


}

function passwordIsTheSame(password, repeatPassword) {
    return password === repeatPassword;
}

function register(email, username, password, playername) {
    let registerValues = { email: email, username: username, password: password, playername: playername};
    eb.send("tetris-21.socket.login.make", JSON.stringify(registerValues), function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });

    eb.registerHandler("tetris-21.socket.login.make.server", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body);
        loginMade = message.body;
        registerMade();
    });
}

function registerMade() {
    if (loginMade === "true") {
        window.location.href = "login.html";
    } else {
        document.querySelector("#errorMessage").style.display = "block";
        document.querySelector("#errorMessage").innerHTML = "Could not make login.";
    }
}

function checkUsername(e) {
    e.preventDefault();
    let username = document.querySelector("#registerUsername").value;
    eb.send("tetris-21.socket.login.username", JSON.stringify({username: username}), function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });
    eb.registerHandler("tetris-21.socket.login.username.server", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body);
        usernameChecker(message.body);
    })
}

function usernameChecker(exists) {
    if (exists === "true") {
        usernameExists = true;
        document.querySelector("#userError").style.display = "block";
        document.querySelector("#userError").innerHTML = "Username does allready exists, use other username.";
    } else {
        usernameExists = false;
    }
}