"use strict";

document.addEventListener("DOMContentLoaded", init);

function init() {
    document.querySelector("#logout").addEventListener("click", logout);
}

function logout(e) {
    e.preventDefault();
    sessionStorage.clear();
    window.location.href = "index.html";
}