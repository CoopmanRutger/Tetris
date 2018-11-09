"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8080/tetris/game");

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
    eb.registerHandler("tetris.game.faction.get", function (error, message) {
        if (error) {
            console.log(error);
        }
        console.log(message);
    });
    eb.send("tetris.game.faction.get",  "faction",function (error, reply) {
        if (error) {
            console.log(error);
        }
        chosenFaction = reply.body;
        console.log(chosenFaction);
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
        window.location.href = "faction_clan.html"; // TODO: make faction html page.
    }
}