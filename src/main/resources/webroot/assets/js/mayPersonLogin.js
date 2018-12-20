"use strict";

mayLogin();

function mayLogin() {
    let loginValues = sessionStorage.getItem("login");
    if (loginValues === null) {
        window.location.href = "login.html";
    } else if (JSON.parse(loginValues).login !== "true") {
        window.location.href = "login.html";
    }
}