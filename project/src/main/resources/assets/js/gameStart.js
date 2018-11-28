"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

let chosenFaction = null;

function init() {
    eb.onopen = function () {
        getFactionInfoFromDB();
    };
    document.querySelector("#goToFaction").addEventListener('click', goToFaction);
    document.querySelector("#goToClan").addEventListener('click', goToClan);
}

function getFactionInfoFromDB() {
    let playerName = "Rutger123";

    eb.send("tetris-21.socket.faction", playerName , function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body);
    });

    eb.registerHandler("tetris-21.socket.faction.get", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message.body);
    });
}

function goToFaction(e) {
    e.preventDefault();
    if (chosenFaction === null) {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html";
    }
}

function goToClan(e) {
    e.preventDefault();
    if (chosenFaction === null) {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html"; // TODO: make clan html page.
    }
}