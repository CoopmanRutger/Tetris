"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");
// let eb = new EventBus("http://172.31.27.98:8021/tetris-21/socket");

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        getFactionInfoFromDB();
    };
    document.querySelector("#goToFaction").addEventListener('click', goToFaction);
    document.querySelector("#goToClan").addEventListener('click', goToClan);
}

function getFactionInfoFromDB() {
    let userId = parseInt(sessionStorage.getItem('UserId'));
    console.log(userId);
    eb.send("tetris-21.socket.gameStart.faction", userId , function (error, reply) {
        if (error) {
            console.log(error)
        }
        console.log(reply.body);
    });

    eb.registerHandler("tetris-21.socket.gameStart.faction.get", function (error, message) {
        if (error) {
            console.log(error);
        }

        console.log(message.body);
        let player = message.body;
        SetFactionInSession(player);
    });
}


function SetFactionInSession(player) {
    console.log(player);
    sessionStorage.setItem('FactionNr', player.FactionNr);
    sessionStorage.setItem('FactionName', player.FactionName);
    sessionStorage.setItem('ClanNr', player.ClanNr);
    sessionStorage.setItem('ClanName', player.ClanName);
}

function goToFaction(e) {
    e.preventDefault();
    let chosenFaction = sessionStorage.getItem('FactionName');
    if (chosenFaction === "dark") {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html";
    }
}

function goToClan(e) {
    e.preventDefault();
    let chosenFaction = sessionStorage.getItem('FactionName');
    if (chosenFaction === "dark") {
        window.location.href = "chooseFaction.html";
    } else {
        window.location.href = "faction_clan.html";
        // TODO: make clan html page.
    }
}