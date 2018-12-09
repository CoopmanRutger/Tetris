"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8021/tetris-21/socket");


document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
    };
}

function chooseFaction(e) {
    e.preventDefault();

    let faction = e.target.alt;
    sessionStorage.setItem('FactionName', faction);
    sendFactionToServer(faction);
    
    if (sessionStorage.getItem('FactionName') != null){
        window.location.href = "faction_clan.html";
    }
}

function sendFactionToServer(faction) {
    eb.send("tetris-21.socket.faction.choose", faction, function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    });
}
