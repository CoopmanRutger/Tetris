"use strict";

document.addEventListener("DOMContentLoaded", init);

let faction;

function init() {
    document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
}

function chooseFaction(e) {
    e.preventDefault();
    faction = e.target.alt;
    // TODO: Process faction to database
}
