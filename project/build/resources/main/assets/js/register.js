"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function() {
        console.log("opened");
    };
    document.querySelector("#register").addEventListener("click", checkValues);
}

function checkValues(e) {
    e.preventDefault();
    let email = document.querySelector("#registerEmail").value;
    let username = document.querySelector("#registerUsername").value;
    let password = document.querySelector("#registerPassword").value;
    let repeatPassword = document.querySelector("#repeatPassword").value;
    let errorMessageElement = document.querySelector("#errorMessage");

    if (email === "" || username === "" || password === "" || repeatPassword === "") {
        errorMessageElement.style.display = "block";
        errorMessageElement.innerHTML = "Not all fields are filled out, please fill in all fields.";
        return;
    }

    if (passwordIsTheSame(password, repeatPassword)) {
        register(email, username, password);
    } else {
        errorMessageElement.style.display = "block";
        errorMessageElement.innerHTML = "The passwords are not the same.";
    }
}

function passwordIsTheSame(password, repeatPassword) {
    return password === repeatPassword;
}

function register(email, username, password) {
    let registerValues = { email: email, username: username, password: password};
    eb.send("tetris-21.socket.login.make", JSON.stringify(registerValues), function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });
}