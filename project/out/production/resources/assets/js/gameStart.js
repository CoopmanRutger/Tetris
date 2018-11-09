"use strict";

document.addEventListener("DOMContentLoaded", init);

let firstTime = true;

function init() {
    getFactionInfoFromDB();
    document.querySelector("#goToFaction").addEventListener('click', goToFaction);
    document.querySelector("#goToClan").addEventListener('click', goToClan);
}

function getFactionInfoFromDB() {
    // TODO: link database info about faction here.
    let chosenFaction = null;
    if (chosenFaction !== null) {
        firstTime = false;
    } else {
        firstTime = true;
    }
}

function goToFaction(e) {
    e.preventDefault();
    if (firstTime === true) {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html";
    }
}

function goToClan(e) {
    e.preventDefault();
    if (firstTime === true) {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html"; // TODO: make faction html page.
    }
}