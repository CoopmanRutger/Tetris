"use strict";

/* global EventBus */
let eb = new EventBus("http://localhost:8000/tetris/infoBackend");
let faction;

document.addEventListener("DOMContentLoaded", init);

function init() {
    eb.onopen = function () {
        document.querySelector("#chooseFaction").addEventListener("click", chooseFaction);
    };
}

function chooseFaction(e) {
    e.preventDefault();
    faction = e.target.alt;
    sendFactionToServer();
}

function sendFactionToServer() {
    eb.send("tetris.infoBackend.faction.choose", faction, function (error, reply) {
        if (error) {
            console.log(error);
        }
        console.log(reply.body);
    })
}
