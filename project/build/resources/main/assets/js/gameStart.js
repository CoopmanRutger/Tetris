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
    let playerName = sessionStorage.getItem('PlayerName');
    console.log(playerName);

    // eb.registerHandler("tetris-21.socket.gameStart.get", function (error, message) {
    //     if (error) {
    //         console.log(error);
    //     }
    //     console.log(message.body);
    //
    // });





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
        let faction = message.body;

        SetFactionInSession(faction);
    });
}


function SetFactionInSession(faction) {
    console.log(faction.userNr);

    sessionStorage.setItem('ClanNr', faction.clanNr);
    sessionStorage.setItem('ClanName', faction.clanName);
    sessionStorage.setItem('FactionName', faction.factionName);
    sessionStorage.setItem('FactionNr', faction.factionNr);
    sessionStorage.setItem('UserNr', faction.userNr);
//     sessionStorage.setItem('heroExp', player.heroExp);
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