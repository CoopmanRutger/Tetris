"use strict";

mayLogin();

function mayLogin() {
    let loginValues = sessionStorage.getItem("index");
    if (loginValues === null) {
        window.location.href = "index.html";
    } else if (JSON.parse(loginValues).login !== "true") {
        window.location.href = "index.html";
    }
}